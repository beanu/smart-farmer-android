package com.beanu.l4_bottom_tab.ui.module1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.base.BaseFragment;
import com.beanu.l4_bottom_tab.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends BaseFragment {


    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

}
