package com.beanu.l4_bottom_tab.ui.module3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.base.BaseFragment;
import com.beanu.arad.support.log.KLog;
import com.beanu.l4_bottom_tab.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 */
public class Fragment3 extends BaseFragment {


    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.d("fragment3 oncreate");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.d("fragment3 onviewcreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.d("fragment3 onresume");
    }

}
