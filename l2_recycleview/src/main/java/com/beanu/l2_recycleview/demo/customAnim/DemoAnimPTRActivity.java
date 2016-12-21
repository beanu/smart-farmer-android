package com.beanu.l2_recycleview.demo.customAnim;

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

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DemoAnimPTRActivity extends AppCompatActivity {

    private PtrAnimFrameLayout mPtrFrame;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_anim_ptr);

        mPtrFrame = (PtrAnimFrameLayout) findViewById(R.id.ptr_frame);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        //TODO TEST DATA
        List<News> allList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            news.setImgPath("http://img.d9soft.com/2016/0412/20160412032431842.png");
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
                MessageUtils.showShortToast(DemoAnimPTRActivity.this, "刷新了");

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
