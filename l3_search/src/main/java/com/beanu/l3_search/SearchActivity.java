package com.beanu.l3_search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;
import com.beanu.l3_search.model.bean.SearchHistoryModel;
import com.beanu.l3_search.model.bean.SearchResultModel;
import com.beanu.l3_search.mvp.contract.SearchContract;
import com.beanu.l3_search.mvp.model.SearchModelImpl;
import com.beanu.l3_search.mvp.presenter.SearchPresenterImpl;

import java.util.ArrayList;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.beanu.l3_search.R.id.ll_search_history;

/**
 * 搜索界面
 */
public class SearchActivity extends ToolBarActivity<SearchPresenterImpl, SearchModelImpl> implements SearchContract.View {

    private CleanEditText mEtSearch;
    private ListView mListViewHistory;
    private LinearLayout mLlSearchEmpty;
    private LinearLayout mLlSearchHistory;
    private RecyclerView mResultList;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    private MultiTypeAdapter mSearchResultAdapter;
    private final Items searchResultModels = new Items();
    private SearchHistoryAdapter searchHistoryAdapter;
    private ArrayList<SearchHistoryModel> histories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEtSearch = findViewById(R.id.et_search);
        mListViewHistory = findViewById(R.id.listView_history);
        mLlSearchEmpty = findViewById(R.id.ll_search_empty);
        mLlSearchHistory = findViewById(ll_search_history);
        mResultList = findViewById(R.id.result_list);

        //设置toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            hideHomeAsUp();
        }
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(mEtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        searchHistoryAdapter = new SearchHistoryAdapter(this, histories);
        searchHistoryAdapter.setOnSearchHistoryListener(new OnSearchHistoryListener() {
            @Override
            public void onDelete(String key) {
                mPresenter.remove(key);
            }

            @Override
            public void onSelect(String content) {
                mEtSearch.setText(content);
                mEtSearch.setSelection(content.length());
                search(content);
            }
        });
        mListViewHistory.setAdapter(searchHistoryAdapter);
        mEtSearch.addTextChangedListener(textWatcher);
        mPresenter.sortHistory();

        mLlSearchEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clear();
            }
        });

        mSearchResultAdapter = new MultiTypeAdapter(searchResultModels);
        mSearchResultAdapter.register(SearchResultModel.class, new SearchResultViewBinder());
        mResultList.setAdapter(mSearchResultAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mResultList.setLayoutManager(linearLayoutManager);
        mResultList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    }

    @Override
    public void showHistories(ArrayList<SearchHistoryModel> results) {
        mLlSearchEmpty.setVisibility(0 != results.size() ? View.VISIBLE : View.GONE);
        searchHistoryAdapter.refreshData(results);
    }

    @Override
    public void searchSuccess(String value) {
//        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        mPresenter.searchResult(value);
    }

    @Override
    public void searchResult(ArrayList<SearchResultModel> resultModels) {

        mLlSearchHistory.setVisibility(View.GONE);
        mResultList.setVisibility(View.VISIBLE);

        searchResultModels.clear();
        searchResultModels.addAll(resultModels);
        mSearchResultAdapter.notifyDataSetChanged();
    }


    public void search(String value) {
        if (!TextUtils.isEmpty(value)) {
            // 先隐藏键盘
            ((InputMethodManager) mEtSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            mPresenter.search(value);
        }
    }

    @Override
    public boolean setupToolBarLeftButton(View leftButton) {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return true;
    }

    @Override
    public boolean setupToolBarRightButton1(View rightButton) {
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(mEtSearch.getText().toString());
            }
        });
        return true;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //在500毫秒内改变时不发送
            if (mHandler.hasMessages(MSG_SEARCH)) {
                mHandler.removeMessages(MSG_SEARCH);
            }
            if (TextUtils.isEmpty(s)) {
                mLlSearchHistory.setVisibility(View.VISIBLE);
                mPresenter.sortHistory();
            } else {
                mLlSearchHistory.setVisibility(View.GONE);
                //否则延迟500ms开始模糊搜索
                Message message = mHandler.obtainMessage();
                message.obj = s;
                message.what = MSG_SEARCH;
                mHandler.sendMessageDelayed(message, 500); //自动搜索功能 删除
            }
        }
    };

    //模糊搜索
    private static final int MSG_SEARCH = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            search(mEtSearch.getText().toString().trim());
        }
    };

}