package com.desmond.citypicker.views.pull2refresh;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desmond.citypicker.R;
import com.desmond.citypicker.views.pull2refresh.callback.IOnItemClickListener;
import com.desmond.citypicker.views.pull2refresh.callback.IOnItemLongClickListener;
import com.desmond.citypicker.views.pull2refresh.callback.IOnRefreshListener;


/**
 * The type Refresh recycler view.
 *
 * @Todo
 * @Author desmond
 * @Date 2016 /12/13
 * @Pacakge com.chinasoft.mengniu.view.pull2refresh
 */
public class RefreshRecyclerView extends RelativeLayout
{
    /**
     * The Pull srl.
     */
    private SwipeRefreshLayout pullSrl;

    /**
     * The Content rv.
     */
    private RecyclerView contentRv;

    /**
     * The Root.
     */
    private View root;

    /**
     * The M status view.
     */
    private View footview;

    /**
     * The M load more view.
     */
    private LinearLayout mLoadMoreView;
    /**
     * The M no more view.
     */
    private TextView mNoMoreView;

    /**
     * The Empty ll.
     */
    private LinearLayout emptyLl;


    /**
     * The Callback.
     */
    private IOnRefreshListener callback;

    /**
     * The Is refreshing.
     */
    private volatile boolean isRefreshing, /**
 * The Is loadding more.
 */
isLoaddingMore, /**
 * The Has more.
 */
hasMore = true;

    /**
     * Instantiates a new Refresh recycler view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public RefreshRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }


    /**
     * Init.
     */
    private void init()
    {
        root = inflate(getContext(), R.layout.pull2refresh_recyclerview, this);
        pullSrl = (SwipeRefreshLayout) root.findViewById(R.id.pull2_refresh_swiperefreshlayout);
        contentRv = (RecyclerView) root.findViewById(R.id.pull2_recycler_recyclerview);
        emptyLl = (LinearLayout) root.findViewById(R.id.pull2_refresh_empty_linearlayout);

        contentRv.setHasFixedSize(true);
        contentRv.setItemAnimator(new DefaultItemAnimator());

        pullSrl.setEnabled(true);
//        ((SimpleItemAnimator)contentRv.getItemAnimator()).setSupportsChangeAnimations(false);

        pullSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                hasMore = true;
                isRefreshing = true;
                if (callback != null)
                    callback.onRefresh();
            }
        });

        contentRv.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) return;
                boolean isBottom = isSlideToBottom();
                if (callback != null && isBottom && !isLoaddingMore && !isRefreshing && hasMore)
                    callback.onLoadMore();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
            }
        });
    }


    /**
     * Is slide to bottom boolean.
     *
     * @return the boolean
     */
    public boolean isSlideToBottom()
    {
        if (contentRv == null || getAdapter() == null) return false;
        return getAdapter().isScoll2Bottom();
    }

    /**
     * Gets last visible position.
     *
     * @return the last visible position
     */
    private int getLastVisiblePosition()
    {
        View lastVisibleChild = contentRv.getChildAt(contentRv.getChildCount() - 1);
        return lastVisibleChild != null ? contentRv.getChildAdapterPosition(lastVisibleChild) : -1;
    }


    /**
     * Show load more footer view.
     */
    public void setLoadMoreLoddingView(View v)
    {
        this.footview = v;

        addFooterView(footview);
    }

    /**
     * Show no more view.
     */
    public void showNoMoreView()
    {
        footview.setVisibility(VISIBLE);
//        mNoMoreView.setVisibility(View.VISIBLE);
        mLoadMoreView.setVisibility(View.GONE);
        hasMore = false;
    }

    /**
     * Show load more view.
     */
    public void showLoadMoreView()
    {
        footview.setVisibility(VISIBLE);
//        mNoMoreView.setVisibility(View.GONE);
        mLoadMoreView.setVisibility(View.VISIBLE);
        isLoaddingMore = true;
        hasMore = true;
    }

    /**
     * Dismiss load more.
     */
    public void dismissLoadMore()
    {
        if (footview != null)
            footview.setVisibility(GONE);
        isLoaddingMore = false;
    }

    /**
     * Sets refreshing.
     */
    public void setRefreshing()
    {
        pullSrl.post(new Runnable()
        {
            @Override
            public void run()
            {
                isRefreshing = true;
                dismissLoadMore();
                pullSrl.setRefreshing(true);
                if (callback != null)
                    callback.onRefresh();
            }
        });
    }

    /**
     * Dismiss refresh.
     */
    public void dismissRefresh()
    {
        pullSrl.post(new Runnable()
        {
            @Override
            public void run()
            {
                isRefreshing = false;
                pullSrl.setRefreshing(false);
            }
        });
    }

    /**
     * Add header view.
     *
     * @param v the v
     */
    public void addHeaderView(View v)
    {
        if (getAdapter() == null) return;
        getAdapter().addHeaderView(v);
    }

    /**
     * Add footer view.
     *
     * @param v the v
     */
    public void addFooterView(View v)
    {
        if (getAdapter() == null) return;
        getAdapter().addFooterView(v);
    }

    /**
     * Remove header view.
     *
     * @param v the v
     */
    public void removeHeaderView(View v)
    {
        if (getAdapter() == null) return;
        getAdapter().removeHeaderView(v);
    }

    /**
     * Remove footer view.
     *
     * @param v the v
     */
    public void removeFooterView(View v)
    {
        if (getAdapter() == null) return;
        getAdapter().removeFooterView(v);
    }

    /**
     * Remove all header view.
     */
    public void removeAllHeaderView()
    {
        if (getAdapter() == null) return;
        getAdapter().removeAllHeaderView();
    }

    /**
     * Sets adapter.
     *
     * @param adapter the adapter
     */
    public void setAdapter(SimpleBaseAdapter adapter)
    {
        adapter.setData(null);
        contentRv.setAdapter(adapter);
    }

    /**
     * Gets adapter.
     *
     * @return the adapter
     */
    public SimpleBaseAdapter getAdapter()
    {
        return (SimpleBaseAdapter) contentRv.getAdapter();
    }

    /**
     * Sets on refresh listener.
     *
     * @param callback the callback
     */
    public void setOnRefreshListener(IOnRefreshListener callback)
    {
        this.callback = callback;
    }

    /**
     * Sets layout manager.
     *
     * @param manager the manager
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager)
    {
        contentRv.setLayoutManager(manager);
    }

    /**
     * Add item decoration.
     *
     * @param decor the decor
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor)
    {
        contentRv.addItemDecoration(decor);
    }


    /**
     * Is loadding more boolean.
     *
     * @return the boolean
     */
    public boolean isLoaddingMore()
    {
        return isLoaddingMore;
    }

    /**
     * Sets loadding more.
     *
     * @param loaddingMore the loadding more
     */
    public void setLoaddingMore(boolean loaddingMore)
    {
        isLoaddingMore = loaddingMore;
    }


    /**
     * Sets on item click listener.
     *
     * @param onItemClickListener the on item click listener
     */
    public void setOnItemClickListener(IOnItemClickListener<?> onItemClickListener)
    {
        if (getAdapter() == null) return;
        getAdapter().setOnItemClickListener(onItemClickListener);
    }

    /**
     * Sets on item long click listener.
     *
     * @param onItemLongClickListener the on item long click listener
     */
    public void setOnItemLongClickListener(IOnItemLongClickListener<?> onItemLongClickListener)
    {
        if (getAdapter() == null) return;
        getAdapter().setOnItemLongClickListener(onItemLongClickListener);
    }


    /**
     * Sets empty view.
     *
     * @param view the view
     */
    public void setEmptyView(View view)
    {
        removeEmptyView();
        emptyLl.addView(view);
    }

    /**
     * Remove empty view.
     */
    public void removeEmptyView()
    {
        emptyLl.removeAllViews();
    }

    /**
     * Show empty view.
     */
    public void showEmptyView()
    {
//        removeAllHeaderView();
        emptyLl.setVisibility(VISIBLE);
    }


    /**
     * Hide empty view.
     */
    public void hideEmptyView()
    {
        emptyLl.setVisibility(GONE);
    }

    public void setColorSchemeResources(@ColorRes int... colorResIds)
    {
        pullSrl.setColorSchemeResources(colorResIds);
    }

    public RecyclerView getRecyclerView()
    {
        return contentRv;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return pullSrl;
    }

    public void disablePullLable()
    {
        pullSrl.setEnabled(false);
    }
}
