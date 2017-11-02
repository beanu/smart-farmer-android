package com.beanu.sf.ui.layer2.recycleview.customAnim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.utils.ToastUtils;
import com.beanu.sf.R;
import com.beanu.sf.ui.layer2.recycleview.support.News;
import com.beanu.sf.ui.layer2.recycleview.support.NewsViewBinder;

import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class DemoAnimPTRActivity extends AppCompatActivity {

    private PtrAnimFrameLayout mPtrFrame;
    private MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_anim_ptr);

        mPtrFrame = (PtrAnimFrameLayout) findViewById(R.id.ptr_frame);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        //TODO TEST DATA
        Items allList = new Items();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            news.setImgPath("http://img.d9soft.com/2016/0412/20160412032431842.png");
            news.setTitle("新闻标题" + i);
            news.setDesc("新闻描述" + i);
            allList.add(news);
        }

        mAdapter = new MultiTypeAdapter(allList);
        mAdapter.register(News.class, new NewsViewBinder());
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
                ToastUtils.showShort("刷新了");

                Observable.timer(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) {
                                mPtrFrame.refreshComplete();
                            }
                        });
            }
        });


    }
}
