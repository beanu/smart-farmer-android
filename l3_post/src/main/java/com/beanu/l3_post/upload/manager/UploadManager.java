package com.beanu.l3_post.upload.manager;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

/**
 *
 * @author lizhihua
 * @date 2017/4/21
 * 上传工具
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class UploadManager<T, R> {

    /**
     * 锁
     */
    private final Object monitor = new Object();

    /**
     * 需要被移除的任务
     */
    private final Set<T> needRemoved = new HashSet<>();

    /**
     * 发生错误的任务
     */
    private final Set<T> errorTask = new HashSet<>();

    /**
     * 上传中任务
     */
    private final Set<T> waitingTask = new HashSet<>();

    /**
     * 完成的任务
     */
    private final Map<T, UploadResponse<T, R>> completedTask = new ConcurrentHashMap<>();

    /**
     * 正在上传的任务的 Disposable
     */
    private final Map<T, Disposable> taskDisposableMap = new ConcurrentHashMap<>();

    private UploadCallback<T, R> uploadCallback;
    private List<ReplaySubject<UploadResponse<T, R>>> recorderList = new CopyOnWriteArrayList<>();

    public UploadManager(UploadCallback<T, R> uploadCallback) {
        this.uploadCallback = uploadCallback;
    }

    public UploadManager() {
    }

    /**
     * 上传单个数据
     */
    public Observable<UploadResponse<T, R>> upload(T source) {
        List<T> tList = new ArrayList<>();
        tList.add(source);
        return upload(tList);
    }

    /**
     * 上传多个数据
     */
    @SuppressWarnings("unchecked")
    public Observable<UploadResponse<T, R>> upload(List<T> tList) {
        checkUploadCallback();

        //拷贝一份，防止在遍历列表时其它线程修改 tList
        final List<T> copyList = new ArrayList<>(tList);

        synchronized (monitor) {
            needRemoved.removeAll(copyList);
            errorTask.removeAll(copyList);
        }

        //不需要网络上传部分，已完成
        List<T> needlessUpload = new ArrayList<>();
        //未上传的数据
        List<T> needUpload = new ArrayList<>();

        synchronized (monitor) {
            for (T t : copyList) {
                if (completedTask.containsKey(t)) {
                    needlessUpload.add(t);
                } else {
                    needUpload.add(t);
                }
            }
        }

        final ReplaySubject<UploadResponse<T, R>> recorder = ReplaySubject.create();
        recorderList.add(recorder);

        return Observable.fromArray(Pair.create(false, needlessUpload), new Pair<>(true, needUpload))
                .flatMap(pair -> pair.first
                        ? networkUpload(pair.second)
                        : noUpload(pair.second)).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(recorder::onNext)
                .doOnComplete(recorder::onComplete);
    }

    /**
     * 等待上传完成
     */
    public Observable<UploadResponse<T, R>> waitComplete() {
        return ReplaySubject.mergeDelayError(recorderList)
                .filter(ftUploadResponse -> ftUploadResponse.status == UploadStatus.STATUS_OK
                        || (ftUploadResponse.status == UploadStatus.STATUS_ERROR && errorTask.contains(ftUploadResponse.source)));
    }

    public boolean isErrorTask(T task) {
        synchronized (monitor) {
            return errorTask.contains(task);
        }
    }

    public boolean hasFailedTask(){
        return !errorTask.isEmpty();
    }

    /**
     * 获取上传完成但是用户取消了的 UploadResponse
     */
    public List<UploadResponse<T, R>> getCanceledTasks() {
        synchronized (monitor) {
            List<UploadResponse<T, R>> tasks = new ArrayList<>();
            Set<Map.Entry<T, UploadResponse<T, R>>> set = completedTask.entrySet();
            for (Map.Entry<T, UploadResponse<T, R>> entry : set) {
                if (entry.getValue().status == UploadStatus.STATUS_CANCEL) {
                    tasks.add(entry.getValue());
                }
            }
            return tasks;
        }
    }

    /**
     * 取消单个上传任务
     */
    public void removeTask(T task) {
        synchronized (monitor) {
            needRemoved.add(task);
            errorTask.remove(task);
            waitingTask.remove(task);

            UploadResponse<T, R> response = completedTask.get(task);
            if (response != null) {
                response.status = UploadStatus.STATUS_CANCEL;
            }

            Disposable disposable = taskDisposableMap.get(task);
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    /**
     * 取消多个上传任务
     */
    public void removeTasks(Collection<T> tasks) {
        synchronized (monitor) {
            needRemoved.addAll(tasks);
            errorTask.removeAll(tasks);
            waitingTask.removeAll(tasks);
            for (T task : tasks) {
                UploadResponse<T, R> response = completedTask.get(task);
                if (response != null) {
                    response.status = UploadStatus.STATUS_CANCEL;
                }

                Disposable disposable = taskDisposableMap.get(task);
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        }
    }

    /**
     * 清除多个出错的任务
     */
    public void clearErrorTasks(Collection<T> tasks) {
        synchronized (monitor) {
            errorTask.removeAll(tasks);
        }
    }

    /**
     * 清除所有出错的任务
     */
    public void clearAllErrorTasks() {
        synchronized (monitor) {
            errorTask.clear();
        }
    }

    /**
     * 清除单个出错的任务
     */
    public void clearErrorTask(T task) {
        synchronized (monitor) {
            errorTask.remove(task);
        }
    }

    /**
     * 根据传入的原始数据返回排好序的上传结果
     */
    public List<UploadResponse<T, R>> getSortedCompleteResults(List<T> tOriginal) {
        List<UploadResponse<T, R>> tList = new ArrayList<>();
        if (tOriginal != null && !tOriginal.isEmpty()) {
            for (T t : tOriginal) {
                UploadResponse<T, R> res = completedTask.get(t);
                if (res != null && res.status == UploadStatus.STATUS_OK) {
                    tList.add(res);
                }
            }
        }
        return tList;
    }

    public UploadResponse<T, R> getCompleteResult(T t){
        synchronized (monitor){
            return completedTask.get(t);
        }
    }

    public void setUploadCallback(UploadCallback<T, R> uploadCallback) {
        this.uploadCallback = uploadCallback;
    }

    private void checkUploadCallback() {
        if (uploadCallback == null) {
            throw new NullPointerException("uploadCallback is null");
        }
    }

    /**
     * 检查列表是否有错误数据
     */
    public static <F, T> void checkHasError(List<UploadResponse<F, T>> responses) {
        if (responses == null || responses.isEmpty()) {
            return;
        }
        List<F> list = new ArrayList<>();
        for (UploadResponse<F, T> response : responses) {
            if (response.status == UploadStatus.STATUS_ERROR) {
                list.add(response.source);
            }
        }
        if (!list.isEmpty()) {
            throw new UploadException(list);
        }
    }

    /**
     * 发射已完成的任务数据
     * @param tList 不需要上传的数据
     * @return Observable
     */
    private Observable<UploadResponse<T, R>> noUpload(final List<T> tList) {
        return Observable.fromIterable(tList)
                .flatMap(t -> {
                    synchronized (monitor) {
                        if (completedTask.containsKey(t)) {
                            UploadResponse<T, R> response = completedTask.get(t);
                            response.status = UploadStatus.STATUS_OK;
                            return Observable.just(response);
                        }
                    }
                    return Observable.empty();
                });
    }

    /**
     * 进行网络上传
     * @param tList 需要使用网络上传的数据
     * @return Observable
     */
    private Observable<UploadResponse<T, R>> networkUpload(final List<T> tList) {
        for (T t : tList) {
            uploadCallback.doOnPreUpload(t);
        }
        //作为 getPreUploadParams 发生错误的标识，并在其中放入 Throwable
        final Map<String, Object> errorMap = new HashMap<>(1);
        return uploadCallback.getPreUploadParams()
                .onErrorReturn(throwable -> {
                    errorMap.put("error", throwable);
                    //通知下游发生失败
                    return errorMap;
                })
                .flatMap(params -> {
                    synchronized (monitor) {
                        //移除正在等待上传的数据，防止重复上传
                        tList.removeAll(waitingTask);
                        //移除已取消的数据
                        tList.removeAll(needRemoved);
                    }

                    //如果上游发生错误, 则 tList 全部失败
                    if (params == errorMap) {
                        synchronized (monitor) {
                            //放入失败列表
                            errorTask.addAll(tList);
                        }
                        final Throwable error = (Throwable) params.get("error");
                        return Observable.fromIterable(tList)
                                .map(t -> new UploadResponse<T, R>(t, null, UploadStatus.STATUS_ERROR, error));
                    }

                    return Observable.fromIterable(tList)
                            .flatMap(task -> {
                                if (completedTask.containsKey(task)) {
                                    UploadResponse<T, R> response = completedTask.get(task);
                                    response.status = UploadStatus.STATUS_OK;
                                    return Observable.just(response);
                                } else {
                                    synchronized (monitor) {
                                        //加入等待列表
                                        waitingTask.add(task);
                                    }
                                    //上传
                                    return uploadCallback.onUpload(task, params)
                                            .doOnSubscribe(disposable -> taskDisposableMap.put(task, disposable))
                                            .map(res -> {
                                                UploadResponse<T, R> response = new UploadResponse<>();
                                                response.source = task;
                                                response.result = res;
                                                synchronized (monitor) {
                                                    //修改资源状态
                                                    response.status = needRemoved.contains(task)
                                                            /* 取消 */ ? UploadStatus.STATUS_CANCEL
                                                            /* 完成 */ : UploadStatus.STATUS_OK;

                                                    //从等待列表移除
                                                    waitingTask.remove(task);
                                                    //放入完成列表
                                                    completedTask.put(task, response);
                                                }
                                                return response;
                                            })
                                            .onErrorReturn(throwable -> {
                                                synchronized (monitor) {
                                                    //从等待列表移除
                                                    waitingTask.remove(task);
                                                    //放入失败列表
                                                    errorTask.add(task);
                                                }
                                                return new UploadResponse<>(task, null, UploadStatus.STATUS_ERROR, throwable);
                                            });
                                }
                            })
                            .filter(ftUploadResponse -> {
                                //过滤掉 取消任务
                                return ftUploadResponse.status != UploadStatus.STATUS_CANCEL;
                            });
                });
    }

}
