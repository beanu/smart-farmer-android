package com.beanu.l2_recyclerview.loadmore;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.beanu.arad.support.listview.ILoadMoreListener;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;


/**
 * load more wrap adapter for recyclerview,user MultiTypeAdapter
 */
public class BaseLoadMoreMultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ITEM_TYPE_LOAD_MORE_VIEW = Integer.MAX_VALUE - 1;

    private MultiTypeAdapter mInnerAdapter;
    private ILoadMoreListener mLoadMoreScrollListener;

    private LoadMoreFooterViewBinder mLoadMoreFooterViewBinder;

    public BaseLoadMoreMultiTypeAdapter(@NonNull MultiTypeAdapter adapter, @NonNull ILoadMoreListener loadMoreListener) {
        this.mInnerAdapter = adapter;
        this.mLoadMoreScrollListener = loadMoreListener;

        mLoadMoreFooterViewBinder = new LoadMoreFooterViewBinder();
        mInnerAdapter.register(LoadMoreFooter.class, mLoadMoreFooterViewBinder);
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (isMoreResult() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && isMoreResult()) {
            ITEM_TYPE_LOAD_MORE_VIEW = mInnerAdapter.getTypePool().firstIndexOf(LoadMoreFooter.class);
            return ITEM_TYPE_LOAD_MORE_VIEW;
        }

        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == ITEM_TYPE_LOAD_MORE_VIEW) {

            ItemViewBinder binder = mInnerAdapter.getTypePool().getItemViewBinders().get(holder.getItemViewType());
            LoadMoreFooter loadmoreFooter = new LoadMoreFooter();
            loadmoreFooter.listener = mLoadMoreScrollListener;

            if (binder instanceof LoadMoreFooterViewBinder) {
                ((LoadMoreFooterViewBinder) binder).onBindViewHolder((LoadMoreFooterViewBinder.ViewHolder) holder, loadmoreFooter);
            }

        } else {
            mInnerAdapter.onBindViewHolder(holder, position, null);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (position == getItemCount() - 1 && isMoreResult()) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (holder.getLayoutPosition() == getItemCount() - 1 && isMoreResult()) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }


    public void setOnFooterListener(LoadMoreFooterViewBinder.OnFooterListener onFooterListener) {
        mLoadMoreFooterViewBinder.setOnLoadListener(onFooterListener);
    }

    private boolean isMoreResult() {
        return (mLoadMoreScrollListener.hasMoreResults() || mLoadMoreScrollListener.hasError());
    }
}
