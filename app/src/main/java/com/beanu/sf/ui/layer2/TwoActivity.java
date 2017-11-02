package com.beanu.sf.ui.layer2;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;
import com.beanu.sf.R;
import com.beanu.sf.model.bean.LayerItem;
import com.beanu.sf.ui.layer1.RetrofitActivity;
import com.beanu.sf.ui.layer1.RxjavaActivity;
import com.beanu.sf.ui.layer2.recycleview.simplest.DemoSimplestActivity;
import com.beanu.sf.ui.layer2.recycleview.support.LayerViewBinder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 第二层功能
 */
public class TwoActivity extends ToolBarActivity {

    private static String[] titles = {"Retrofit", "Rxjava", "RecyclerView"};
    private static Class[] className = {RetrofitActivity.class, RxjavaActivity.class, DemoSimplestActivity.class};

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
        MultiTypeAdapter layerAdapter = new MultiTypeAdapter(itemList);
        layerAdapter.register(LayerItem.class, new LayerViewBinder());
        mRecycleViewOne.setLayoutManager(new LinearLayoutManager(this));
        mRecycleViewOne.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mRecycleViewOne.setAdapter(layerAdapter);
    }

}