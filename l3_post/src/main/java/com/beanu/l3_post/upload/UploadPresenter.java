package com.beanu.l3_post.upload;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.beanu.arad.base.BasePresenter;
import com.beanu.l3_post.upload.manager.UploadCallback;
import com.beanu.l3_post.upload.manager.UploadManager;
import com.beanu.l3_post.upload.manager.UploadResponse;
import com.beanu.l3_post.upload.manager.UploadStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @param <T> 上传数据类型，需覆写 hashCode() 方法
 * @param <R> 服务器返回数据类型
 * @param <V> MVP V 层 需继承至 IUploadViewMVP<T, R>
 * @param <M> MVP M 层 需继承至 IUploadModel<T, R>
 * @author lizhi
 *         <p>
 *         MVP 上传 Presenter
 * @see IUploadView
 * @see IUploadModel
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class UploadPresenter<T, R, V extends IUploadView<T, R>, M extends IUploadModel<T, R>>
        extends BasePresenter<V, M> implements UploadCallback<T, R>, IProgressListener<T> {

    /**
     * 上传进度
     */
    private static final int MES_PROGRESS = 3232;
    /**
     * 上传预处理
     */
    private static final int MES_PRE_UPLOAD = 3233;
    /**
     * 上传进度表
     */
    private final Map<T, Double> progressMap = new WeakHashMap<>();

    protected final UploadManager<T, R> uploadManager = new UploadManager<>(this);
    @NonNull
    protected final Thread myThread;

    @SuppressWarnings("unchecked")
    protected final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            T task = (T) message.obj;
            if (task == null) {
                return false;
            }
            if (message.what == MES_PROGRESS) {
                Double progress = progressMap.get(task);
                if (progress != null) {
                    mView.onUploadInProgress(task, progress);
                }
                return true;
            } else if (message.what == MES_PRE_UPLOAD) {
                mView.beforeUpload(task);
                return true;
            }
            return false;
        }
    });

    public UploadPresenter() {
        myThread = Thread.currentThread();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteUnused(uploadManager.getCanceledTasks());
    }

    /**
     * 开始上传任务
     *
     * @param tList 任务列表
     */
    public void upload(@NonNull List<T> tList) {

        final List<T> copyList = new ArrayList<>(tList);
        uploadManager.upload(copyList)
                .subscribe(new Observer<UploadResponse<T, R>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mRxManage.add(d);
                    }

                    @Override
                    public void onNext(UploadResponse<T, R> trUploadResponse) {
                        if (trUploadResponse.status == UploadStatus.STATUS_OK) {
                            mView.onUploadSuccess(trUploadResponse.source, trUploadResponse.result);
                        } else if (trUploadResponse.status == UploadStatus.STATUS_ERROR) {
                            mView.onUploadFailed(trUploadResponse.source, trUploadResponse.error);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //notice 进入这里的概率低到没有
                        List<UploadResponse<T, R>> complete = uploadManager.getSortedCompleteResults(copyList);
                        for (UploadResponse<T, R> response : complete) {
                            copyList.remove(response.source);
                        }
                        mView.onUploadComplete(complete, copyList);
                    }

                    @Override
                    public void onComplete() {
                        List<UploadResponse<T, R>> complete = uploadManager.getSortedCompleteResults(copyList);
                        for (UploadResponse<T, R> response : complete) {
                            copyList.remove(response.source);
                        }
                        mView.onUploadComplete(complete, copyList);
                    }
                });
    }

    public void upload(@NonNull T item) {
        upload(Collections.singletonList(item));
    }

    /**
     * 移除某个上传任务
     *
     * @param t 上传任务
     */
    public void removeTask(T t) {
        uploadManager.removeTask(t);
    }

    /**
     * 移除某些上传任务
     *
     * @param tList 上传任务
     */
    public void removeTasks(List<T> tList) {
        uploadManager.removeTasks(tList);
    }

    /**
     * 等待上传任务完成
     *
     * @param uploadList 上传数据列表
     * @return 如果上传过程中出现错误，会抛出 UploadException 异常，否则根据 uploadList 发射 上传完成数据
     */
    public Observable<List<UploadResponse<T, R>>> waitUploadComplete(final List<T> uploadList) {
        return uploadManager.waitComplete()
                .toList().toObservable()
                .map(uploadResponses -> {
                    //检查是否有错误
                    UploadManager.checkHasError(uploadResponses);
                    return uploadManager.getSortedCompleteResults(uploadList);
                });
    }

    /**
     * 删除没有使用但是已经上传到服务器的数据，页面结束时会调用此方法，子类需覆写实现此方法
     *
     * @param trList 无用数据
     */
    public void deleteUnused(@NonNull List<UploadResponse<T, R>> trList) {

    }

    @NonNull
    @Override
    public Observable<Map<String, Object>> getPreUploadParams() {
        return mModel.getPreUploadParams()
                .doOnSubscribe(disposable -> mRxManage.add(disposable));
    }

    @Override
    public void doOnPreUpload(@NonNull final T t) {
        if (Thread.currentThread() == myThread) {
            mView.beforeUpload(t);
        } else {
            Message message = Message.obtain(handler);
            message.what = MES_PRE_UPLOAD;
            message.obj = t;
            message.sendToTarget();
        }
    }

    @NonNull
    @Override
    public Observable<R> onUpload(T t, Map<String, Object> params) {
        return mModel.onUpload(t, params, this)
                .doOnSubscribe(disposable -> mRxManage.add(disposable));
    }

    @Override
    public void progress(final T t, final double percent) {
        if (Thread.currentThread() == myThread) {
            mView.onUploadInProgress(t, percent);
        } else {
            progressMap.put(t, percent);
            Message message = Message.obtain(handler);
            message.what = MES_PROGRESS;
            message.obj = t;
            message.sendToTarget();
        }
    }
}
