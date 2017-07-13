package com.beanu.sf.ui.layer3.search;

import android.os.Bundle;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_search.SearchActivity;
import com.beanu.sf.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDemoActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        startActivity(SearchActivity.class);
    }
}
