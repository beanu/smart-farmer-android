package com.beanu.l4_bottom_tab.ui.module1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.base.BaseFragment;
import com.beanu.arad.support.log.KLog;
import com.beanu.l4_bottom_tab.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends BaseFragment {


    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KLog.d("fragment1 oncreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        KLog.d("fragment1 onviewcreate");
    }


    @Override
    public void onResume() {
        super.onResume();
        /**
         * 底部按钮 切换 只会重新走这个方法
         */
        KLog.d("fragment1 onresume");
    }


}