package com.beanu.sf;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;
import com.beanu.sf.model.bean.LayerItem;
import com.beanu.sf.ui.layer1.RetrofitActivity;
import com.beanu.sf.ui.layer1.RxjavaActivity;
import com.beanu.sf.ui.layer2.recycleview.simplest.DemoSimplestActivity;
import com.beanu.sf.ui.layer2.recycleview.support.LayerViewBinder;
import com.beanu.sf.ui.layer3.guide.GuideDemoActivity;
import com.beanu.sf.ui.layer3.login.LoginDemoActivity;
import com.beanu.sf.ui.layer3.search.SearchDemoActivity;
import com.beanu.sf.ui.layer3.shoppingcart.CartDemoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 功能列表
 */
public class MainListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    Unbinder unbinder;

    private int mPosition;

    private static String[] titles1 = {"Retrofit基础用法", "Rxjava基础用法"};
    private static Class[] className1 = {RetrofitActivity.class, RxjavaActivity.class};

    private static String[] titles2 = {"图片选择器", "二维码扫描", "支付宝+微信支付", "分享／第三方登录", "聊天IM", "RecycleView的各种用法（最简用法）"};
    private static Class[] className2 = {RetrofitActivity.class, RxjavaActivity.class, DemoSimplestActivity.class, DemoSimplestActivity.class, DemoSimplestActivity.class, DemoSimplestActivity.class};

    private static String[] titles3 = {"登录模块", "购物车模块", "引导页模块", "搜索模块"};
    private static Class[] className3 = {LoginDemoActivity.class, CartDemoActivity.class, GuideDemoActivity.class, SearchDemoActivity.class};

    public MainListFragment() {
        // Required empty public constructor
    }

    public static MainListFragment newInstance(int param1) {
        MainListFragment fragment = new MainListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<LayerItem> itemList = new ArrayList<>();

        switch (mPosition) {
            case 0:
                for (int i = 0; i < titles1.length; i++) {
                    LayerItem item = new LayerItem();
                    item.setTitle(titles1[i]);
                    item.setClsName(className1[i]);
                    itemList.add(item);
                }
                break;
            case 1:
                for (int i = 0; i < titles2.length; i++) {
                    LayerItem item = new LayerItem();
                    item.setTitle(titles2[i]);
                    item.setClsName(className2[i]);
                    itemList.add(item);
                }
                break;
            case 2:
                for (int i = 0; i < titles3.length; i++) {
                    LayerItem item = new LayerItem();
                    item.setTitle(titles3[i]);
                    item.setClsName(className3[i]);
                    itemList.add(item);
                }
                break;
            case 3:

                break;
        }

        //设置recycle view
        MultiTypeAdapter layerAdapter = new MultiTypeAdapter(itemList);
        layerAdapter.register(LayerItem.class, new LayerViewBinder());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).colorResId(R.color.base_line).size(1).showLastDivider().build());
        mRecycleView.setAdapter(layerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
