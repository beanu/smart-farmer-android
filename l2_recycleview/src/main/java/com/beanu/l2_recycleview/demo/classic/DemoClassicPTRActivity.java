package com.beanu.l2_recycleview.demo.classic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.utils.MessageUtils;
import com.beanu.l2_recycleview.R;
import com.beanu.l2_recycleview.demo.support.News;
import com.beanu.l2_recycleview.demo.support.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DemoClassicPTRActivity extends AppCompatActivity {

    private PtrClassicFrameLayout mPtrFrame;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_classic_ptr);


        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        //TODO TEST DATA
        List<News> allList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            news.setImgPath("http://ds.cdncache.org/avatar-50/532/164695.jpg");
            news.setTitle("新闻标题" + i);
            news.setDesc("新闻描述" + i);
            allList.add(news);
        }

        mAdapter = new RecyclerAdapter(this, allList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 1000);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                MessageUtils.showShortToast(DemoClassicPTRActivity.this, "刷新了");

                Observable.timer(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                mPtrFrame.refreshComplete();
                            }
                        });
            }
        });

    }

}
