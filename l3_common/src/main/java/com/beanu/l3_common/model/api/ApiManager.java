package com.beanu.l3_common.model.api;


import android.util.Log;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MD5Util;
import com.beanu.l3_common.util.Constants;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jam on 16-5-17
 * Description:
 */
public class ApiManager {

    private static Map<Class, Object> apiServiceMap = new HashMap<>();


    /**
     * 获取ApiService
     *
     * @param service APiService类型
     * @param <T>     范型参数
     * @return
     */
    public static <T> T getService(final Class<T> service) {
        T api = (T) apiServiceMap.get(service);
        if (api == null) {
            Object o = createService(service);
            apiServiceMap.put(service, o);
            return (T) o;
        }
        return (T) apiServiceMap.get(service);
    }

    /**
     * 获取ApiService
     *
     * @param service APiService类型
     * @param baseUrl 请求接口地址
     * @param <T>     范型参数
     * @return
     */
    public static <T> T getServiceWithBaseUrl(Class<T> service, String baseUrl) {
        T api = (T) apiServiceMap.get(service);
        if (api == null) {
            Object o = createServiceWithBaseUrl(service, baseUrl);
            apiServiceMap.put(service, o);
            return (T) o;
        }
        return (T) apiServiceMap.get(service);
    }

    private static <T> T createService(Class<T> service) {
        return createServiceWithBaseUrl(service, Constants.URL);
    }

    private static <T> T createServiceWithBaseUrl(Class<T> service, String baseUrl) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .addNetworkInterceptor(new CacheInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build().create(service);
    }

    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();

            HttpUrl httpUrl = original.url();

            //可排序的map
            TreeMap<String, String> params = new TreeMap<>(new Comparator<String>() {

                @Override
                public int compare(String o, String t1) {
                    return o.compareTo(t1);
                }
            });

            //1.把get中参数加入进去
            for (int i = 0; i < httpUrl.querySize(); i++) {
                params.put(httpUrl.queryParameterName(i), httpUrl.queryParameterValue(i));
            }

            //2.把post中的参数加入进去
            if (original.body() instanceof FormBody) {
                FormBody body = (FormBody) original.body();
                for (int i = 0; i < body.size(); i++) {
                    params.put(body.name(i), body.value(i));
                }
            }

            //3.开始拼接加密的字符串
//            String urlPath = httpUrl.url().getPath() + "?";
            String urlPath = "";
            Iterator iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = params.get(key);
                urlPath = urlPath + key + "=" + value + "&";
            }

            //4.把uuid和时间戳加上
            urlPath = urlPath + original.header("uuid") + "&" + original.header("timestamp");

//            KLog.d("排序后" + urlPath);
            String sign = MD5Util.md5String(urlPath);
//            KLog.d("MD5:" + sign);

            Request.Builder requestBuilder = original.newBuilder()
                    .header("sign", sign);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    private static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!AndroidUtil.networkStatusOK(Arad.app)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (AndroidUtil.networkStatusOK(Arad.app)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

}
