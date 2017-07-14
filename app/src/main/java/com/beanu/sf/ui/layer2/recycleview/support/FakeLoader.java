package com.beanu.sf.ui.layer2.recycleview.support;

import com.beanu.arad.http.IPageModel;
import com.beanu.arad.http.RxHelper;
import com.beanu.sf.ui.layer2.recycleview.support.api.DemoHttpModel;
import com.beanu.sf.ui.layer2.recycleview.support.api.DemoPageModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * 虚拟网络请求
 * Created by lizhihua on 2016/11/1.
 */

public class FakeLoader {
    public static Observable<IPageModel<News>> loadNewsList(final int page) {
        return Observable.create(new ObservableOnSubscribe<DemoHttpModel<IPageModel<News>>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<DemoHttpModel<IPageModel<News>>> subscriber) throws Exception {
                DemoHttpModel<IPageModel<News>> baseModel = new DemoHttpModel<>();
                baseModel.error = "false";
                DemoPageModel<News> pageModel = new DemoPageModel<>();
                pageModel.currentPage = page;
                pageModel.totalPage = 10;
                pageModel.dataList = new ArrayList<>();
                for (int i = 0; i < 10; ++i) {
                    News standard = new News();
                    standard.setTitle("新闻标题" + page + "" + i);
                    standard.setDesc("征收标准：自2015年9月1日起，适当调整我省水资源费征收标准。淮河流域及合肥市、滁州市地表水资源费征收标准为每立方米0.12元;其他地区为每立方米0.08元。其中，水力发电用水水资源费征收标准0.003元/千万时，贯流式火电为0.001元/千万时，抽水蓄能电站发电循环用水量暂不征收水资源费。");
                    standard.setImgPath("http://ds.cdncache.org/avatar-50/532/164695.jpg");
                    pageModel.dataList.add(standard);
                }
                baseModel.results = pageModel;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    baseModel.error = "111";
                    baseModel.msg = "超时";
                }

                if (Math.random() > 0.5) {
                    subscriber.onError(new Throwable());
                } else {
                    subscriber.onNext(baseModel);
                }

                subscriber.onComplete();
            }
        }).compose(RxHelper.<IPageModel<News>>handleResult());
    }

}
