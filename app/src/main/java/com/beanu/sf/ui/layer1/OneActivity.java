package com.beanu.sf.ui.layer1;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;
import com.beanu.sf.R;
import com.beanu.sf.adapter.LayerAdapter;
import com.beanu.sf.model.bean.LayerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 第一层功能
 */
public class OneActivity extends ToolBarActivity {

    private static String[] titles = {"Retrofit", "Rxjava"};
    private static Class[] className = {RetrofitActivity.class, RxjavaActivity.class};

    @BindView(R.id.recycle_view_one) RecyclerView mRecycleViewOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

        //初始化数据
        List<LayerItem> itemList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            LayerItem item = new LayerItem();
            item.setTitle(titles[i]);
            item.setClsName(className[i]);
            itemList.add(item);
        }

        //设置recycle view
        LayerAdapter layerAdapter = new LayerAdapter(this, itemList);
        mRecycleViewOne.setLayoutManager(new LinearLayoutManager(this));
        mRecycleViewOne.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mRecycleViewOne.setAdapter(layerAdapter);
    }

}