package com.beanu.l2_recycleview.demo.storehouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.beanu.l2_recycleview.R;
import com.beanu.l2_recycleview.demo.support.News;
import com.beanu.l2_recycleview.demo.support.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class StoreHouseActivity extends AppCompatActivity {

    final String[] mStringList = {"Beanu", "Smart Farmer"};

    private PtrFrameLayout mPtrFrame;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_house);


        mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
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

        // header
        final StoreHouseHeader header = new StoreHouseHeader(this);
        header.setPadding(0, AndroidUtil.dp2px(this, 15), 0, 0);
        header.setTextColor(getResources().getColor(R.color.colorPrimary));
        header.initWithString(mStringList[0]);

        // for changing string
        mPtrFrame.addPtrUIHandler(new PtrUIHandler() {

            private int mLoadTime = 0;

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                header.initWithString(string);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                String string = mStringList[mLoadTime % mStringList.length];
                MessageUtils.showLongToast(StoreHouseActivity.this, string);
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });


        mPtrFrame.setDurationToCloseHeader(3000);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(false);
            }
        }, 1000);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 2000);
            }
        });


    }
}
