package com.beanu.l2_recycleview.demo.support;

import com.beanu.arad.http.IPageModel;
import com.beanu.arad.http.RxHelper;
import com.beanu.l2_recycleview.demo.support.api.HttpModel;
import com.beanu.l2_recycleview.demo.support.api.PageModel;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * 虚拟网络请求
 * Created by lizhihua on 2016/11/1.
 */

public class FakeLoader {
    public static Observable<IPageModel<News>> loadNewsList(final int page) {
        return Observable.create(new Observable.OnSubscribe<HttpModel<IPageModel<News>>>() {
            @Override
            public void call(Subscriber<? super HttpModel<IPageModel<News>>> subscriber) {
                HttpModel<IPageModel<News>> baseModel = new HttpModel<>();
                baseModel.error = "false";
                PageModel<News> pageModel = new PageModel<>();
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

                subscriber.onNext(baseModel);
                subscriber.onCompleted();
            }
        }).compose(RxHelper.<IPageModel<News>>handleResult());
    }

}
