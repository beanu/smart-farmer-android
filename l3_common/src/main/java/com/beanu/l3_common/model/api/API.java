package com.beanu.l3_common.model.api;

/**
 * 对外提供统一的获取请求服务实例
 * Created by Beanu on 2017/3/28.
 */

public class API {

    /**
     * 获取一般请求的服务实例
     *
     * @param clazz 模块服务对应的实例类型
     * @param <T>   泛型
     * @return 指定的服务类型的实例
     */
    public static <T> T getInstance(Class<T> clazz) {
        return APIManager.getService(clazz);
    }

    /**
     * 获取指定Url请求的服务实例
     *
     * @param clazz   服务实例类型
     * @param baseUrl 指定请求接口地址
     * @param <T>     泛型
     * @return 指定的服务类型的实例
     */
    public static <T> T getInstanceWithBaseUrl(Class<T> clazz, String baseUrl) {
        return APIManager.getServiceWithBaseUrl(clazz, baseUrl);
    }

}
