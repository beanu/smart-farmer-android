package com.beanu.l3_post.upload;//package com.beanu.l2_post.mvp;
//
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.util.ArrayMap;
//import android.support.v4.util.Pair;
//
//import com.beanu.arad.Arad;
//import com.beanu.arad.error.AradException;
//import com.beanu.arad.utils.ImageUtils;
//import com.beanu.l3_common.model.RxHelper;
//import com.beanu.l3_common.model.api.API;
//import com.beanu.l3_common.model.api.ApiService;
//import com.beanu.l3_common.util.Constants;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.bumptech.glide.request.transition.Transition;
//import com.google.gson.JsonObject;
//import com.qiniu.android.common.AutoZone;
//import com.qiniu.android.storage.Configuration;
//import com.qiniu.android.storage.UpProgressHandler;
//import com.qiniu.android.storage.UploadManager;
//import com.qiniu.android.storage.UploadOptions;
//import com.yanzhenjie.album.AlbumFile;
//
//import org.json.JSONObject;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//import io.reactivex.Observable;
//
//import static android.graphics.Bitmap.CompressFormat.JPEG;
//
///**
// * @author lizhi
// * @date 2017/11/22.
// */
//
//public class QiniuUploadModel implements IUploadModel<AlbumFile, JsonObject> {
//
//    private static Configuration config = new Configuration.Builder().zone(AutoZone.autoZone).build();
//    private static UploadManager uploadManager = new UploadManager(config);
//    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
//
//    /**
//     * 超时 5分钟
//     */
//    private static final long TIMEOUT = 1000 * 60 * 5;
//    private long timestamp = 0;
//    private final Map<String, Object> params = new HashMap<>();
//
//    @NonNull
//    @Override
//    public Observable<Map<String, Object>> getPreUploadParams() {
//        long time = System.currentTimeMillis();
//        if (time - timestamp > TIMEOUT || !params.containsKey("token")) {
//            timestamp = time;
//            return API.getInstance(ApiService.class)
//                    .getQnToken(Constants.GET_QINIU_TOKEN)
//                    .compose(RxHelper.handleResult())
//                    .map(token -> {
//                        params.put("token", token);
//                        return params;
//                    });
//        } else {
//            return Observable.just(params);
//        }
//    }
//
//    @NonNull
//    @Override
//    public Observable<JsonObject> onUpload(AlbumFile albumFile, Map<String, Object> params, IProgressListener<AlbumFile> listener) {
//        if (albumFile.getMediaType() == AlbumFile.TYPE_VIDEO) {
//            return uploadVideo(albumFile, params, listener);
//        } else {
//            return uploadPicture(albumFile, params, listener);
//        }
//    }
//
//    private Observable<JsonObject> uploadPicture(AlbumFile albumFile, Map<String, Object> params, IProgressListener<AlbumFile> listener) {
//        final String expectKey = getExpectKey(albumFile);
//        String token = params.get("token").toString();
//        UpProgressHandler progressHandler = (key, percent) -> listener.progress(albumFile, percent);
//
//        return qiniuUpload(albumFile.getPath(), false, expectKey, token, progressHandler)
//                .flatMap(jsonObject -> {
//                    Map<String, Object> req = new ArrayMap<>();
//                    req.put("size", jsonObject.opt("size"));
//                    req.put("height", jsonObject.opt("h"));
//                    req.put("width", jsonObject.opt("w"));
//                    req.put("key", jsonObject.opt("key"));
//                    return API.getInstance(ApiService.class)
//                            .post(Constants.UPLOAD_QINIU_PIC_NEW, req)
//                            .compose(RxHelper.handleJsonResult())
//                            .map(jsonObject1 -> jsonObject1.get("data").getAsJsonObject());
//                });
//    }
//
//    private Observable<JsonObject> uploadVideo(AlbumFile albumFile, Map<String, Object> params, IProgressListener<AlbumFile> listener) {
//        String coverExpectKey = "qiniu_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000.0D) + "_" + (int) (Math.random() * 1000.0D);
//        final String expectKey = getExpectKey(albumFile);
//        String token = params.get("token").toString();
//        final double[] totalPercent = {0, 0};
//        UpProgressHandler coverProgressHandler = (key, percent) -> {
//            totalPercent[0] = percent;
//            listener.progress(albumFile, (totalPercent[0] + totalPercent[1]) / 2);
//        };
//        UpProgressHandler videoProgressHandler = (key, percent) -> {
//            totalPercent[1] = percent;
//            listener.progress(albumFile, (totalPercent[0] + totalPercent[1]) / 2);
//        };
//
//        return getVideoCover(albumFile.getPath())
//                .flatMap(s -> Observable.zip(
//                        //封面图片
//                        qiniuUpload(s, false, coverExpectKey, token, coverProgressHandler),
//                        //视频
//                        qiniuUpload(albumFile.getPath(), true, expectKey, token, videoProgressHandler),
//                        //打包
//                        Pair::create
//                ))
//                .flatMap(pair -> {
//                    JSONObject coverJson = pair.first;
//                    Map<String, Object> req = new ArrayMap<>();
//                    req.put("pic_width", coverJson.opt("w"));
//                    req.put("pic_height", coverJson.opt("h"));
//                    req.put("pic_size", coverJson.opt("size"));
//                    req.put("pic_key", coverJson.opt("key"));
//                    req.put("video_key", expectKey);
//                    return API.getInstance(ApiService.class)
//                            .post(Constants.UPLOAD_QINIU_VIDEO, req)
//                            .compose(RxHelper.handleJsonResult());
//                });
//    }
//
//    private Observable<JSONObject> qiniuUpload(String path, boolean isVideo, String expectKey, String token, UpProgressHandler progressHandler) {
//        return Observable.create(emitter -> {
//            uploadManager.put(path, expectKey, token,
//                    (k, rinfo, response) -> {
//                        try {
//                            if (response == null) {
//                                timestamp = 0;
//                                if (!emitter.isDisposed()) {
//                                    emitter.onError(new NullPointerException("null response"));
//                                }
//                                return;
//                            }
//                            if (response.optBoolean("success")) {
//                                //视频返回数据里面没有data
//                                if (isVideo) {
//                                    emitter.onNext(response);
//                                } else {
//                                    emitter.onNext(response.getJSONObject("data"));
//                                }
//                                emitter.onComplete();
//                            } else {
//                                AradException exception = new AradException(response.optString("msg"));
//                                exception.setError_code(response.optString("code"));
//                                if (!emitter.isDisposed()) {
//                                    emitter.onError(exception);
//                                }
//                            }
//                        } catch (Exception e) {
//                            //重新获取token
//                            timestamp = 0;
//                            if (!emitter.isDisposed()) {
//                                emitter.onError(e);
//                            }
//                        }
//                    }, new UploadOptions(null, null, true, progressHandler, emitter::isDisposed)
//            );
//        });
//    }
//
//    private Observable<String> getVideoCover(String videoPath) {
//        return Observable.create(emitter -> {
//            Glide.with(Arad.app).asBitmap().load(videoPath).into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                    try {
//                        File file = new File(videoPath);
//                        String name = file.getName();
//                        int lastPointPos = name.lastIndexOf('.');
//                        if (lastPointPos != -1) {
//                            name = name.substring(0, lastPointPos);
//                        }
//                        name = file.getParent() + File.separator + name + "_tb.jpg";
//                        if (ImageUtils.save(resource, name, JPEG, true)) {
//                            emitter.onNext(name);
//                            emitter.onComplete();
//                        } else {
//                            if (!emitter.isDisposed()) {
//                                emitter.onError(new RuntimeException("获取封面图片失败"));
//                            }
//                        }
//                    } catch (Exception e) {
//                        if (!emitter.isDisposed()) {
//                            emitter.onError(e);
//                        }
//                    }
//                }
//
//                @Override
//                public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                    if (!emitter.isDisposed()) {
//                        emitter.onError(new RuntimeException("获取封面图片失败"));
//                    }
//                }
//            });
//        });
//    }
//
//    private String getExpectKey(AlbumFile albumFile) {
//        int lastPointPos = albumFile.getPath().lastIndexOf('.');
//        String suffix = "";
//        if (lastPointPos != -1) {
//            suffix = albumFile.getPath().substring(lastPointPos);
//        }
//        if (albumFile.getMediaType() == AlbumFile.TYPE_VIDEO) {
//            return "video/" + dateFormat.format(new Date()) + "/" + System.currentTimeMillis() + (int) (Math.random() * 1000.0D) + "_" + (int) (Math.random() * 1000.0D) + suffix;
//        } else {
//            return "pic/" + dateFormat.format(new Date()) + "/" + System.currentTimeMillis() + (int) (Math.random() * 1000.0D) + "_" + (int) (Math.random() * 1000.0D) + suffix;
//        }
//    }
//}
