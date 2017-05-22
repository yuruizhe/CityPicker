package com.desmond.citypicker.views.pull2refresh;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.desmond.citypicker.views.pull2refresh.callback.IOnItemClickListener;
import com.desmond.citypicker.views.pull2refresh.callback.IOnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Simple base adapter.
 *
 * @param <T> the type parameter
 * @Todo
 * @Author desmond
 * @Date 2016 /12/13
 * @Pacakge com.chinasoft.mengniu.view.pull2refresh
 */
public class SimpleBaseAdapter<T> extends RecyclerView.Adapter
{
    /**
     * The Context.
     */
    protected Context context;
    /**
     * The List.
     */
    private List<T> list;

    /**
     * The Header views.
     */
    private SparseArrayCompat<View> headerViews, /**
 * The Footer views.
 */
footerViews;

    /**
     * The constant BASE_ITEM_HEADER_KEY.
     */
    private static final int BASE_ITEM_HEADER_KEY = 10000;
    /**
     * The constant BASE_ITEM_FOOTER_KEY.
     */
    private static final int BASE_ITEM_FOOTER_KEY = 20000;


    /**
     * The On click listener.
     */
    private View.OnClickListener onClickListener;
    /**
     * The On item click listener.
     */
    private IOnItemClickListener<T> onItemClickListener;
    /**
     * The On item long click listener.
     */
    private IOnItemLongClickListener<T> onItemLongClickListener;


    /**
     * 是否滚动到底部
     */
    private boolean isScoll2Bottom;


    /**
     * Instantiates a new Simple base adapter.
     *
     * @param context the context
     */
    public SimpleBaseAdapter(Context context)
    {
        this.context = context;
        headerViews = new SparseArrayCompat<>();
        footerViews = new SparseArrayCompat<>();
    }


    /**
     * Sets on click listener.
     *
     * @param onClickListener the on click listener
     */
    public void setOnClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    /**
     * Sets on item click listener.
     *
     * @param onItemClickListener the on item click listener
     */
    public void setOnItemClickListener(IOnItemClickListener<T> onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Sets on item long click listener.
     *
     * @param onItemLongClickListener the on item long click listener
     */
    public void setOnItemLongClickListener(IOnItemLongClickListener<T> onItemLongClickListener)
    {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * Sets data.
     *
     * @param list the list
     */
    public void setData(List<T> list)
    {
        this.list = list == null ? new ArrayList<T>(0) : list;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public List<T> getData()
    {
        return list;
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount()
    {
        return list.size() + getHeaderSize() + getFooterSize();
    }

    /**
     * Gets real item count.
     *
     * @return the real item count
     */
    public int getRealItemCount()
    {
        return list.size();
    }

    /**
     * Add header view.
     *
     * @param v the v
     */
    public void addHeaderView(View v)
    {
        headerViews.put(BASE_ITEM_HEADER_KEY + getHeaderSize(), v);
    }

    /**
     * Add footer view.
     *
     * @param v the v
     */
    public void addFooterView(View v)
    {
        footerViews.put(BASE_ITEM_FOOTER_KEY + getFooterSize(), v);
    }

    /**
     * Remove header view.
     *
     * @param v the v
     */
    public void removeHeaderView(View v)
    {
        for (int i = 0; i < headerViews.size(); i++)
        {
            View hv = headerViews.valueAt(i);
            if(hv == v)
            {
                headerViews.removeAt(i);
                return;
            }
        }
    }

    /**
     * Remove all header view.
     */
    public void removeAllHeaderView()
    {
        headerViews.clear();
    }

    /**
     * Remove footer view.
     *
     * @param v the v
     */
    public void removeFooterView(View v)
    {
        for (int i = 0; i < footerViews.size(); i++)
        {
            View fv = footerViews.valueAt(i);
            if(fv == v)
            {
                footerViews.removeAt(i);
                return;
            }
        }
    }

    /**
     * Remove all footer view.
     */
    public void removeAllFooterView()
    {
        footerViews.clear();
    }

    /**
     * Gets header size.
     *
     * @return the header size
     */
    public int getHeaderSize()
    {
        return headerViews.size();
    }

    /**
     * Gets footer size.
     *
     * @return the footer size
     */
    public int getFooterSize()
    {
        return footerViews.size();
    }


    /**
     * Is header view position boolean.
     *
     * @param position the position
     * @return the boolean
     */
    public boolean isHeaderViewPosition(int position)
    {
        return position < getHeaderSize();
    }

    /**
     * Is footer view position boolean.
     *
     * @param position the position
     * @return the boolean
     */
    public boolean isFooterViewPosition(int position)
    {
        int start = getHeaderSize() + getRealItemCount();
        int end = getItemCount();
        return position >= start && position < end;
    }

    /**
     * Inflate view.
     *
     * @param layout the layout
     * @param parent the parent
     * @return the view
     */
    protected View inflate(int layout, ViewGroup parent)
    {
        return LayoutInflater.from(this.context).inflate(layout, parent, false);
    }

    /**
     * On create view holder recycler view . view holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the recycler view . view holder
     */
    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (headerViews.get(viewType) != null)
        {
            return new BaseViewHolder(headerViews.get(viewType));
        }

        if (footerViews.get(viewType) != null)
        {
            return new BaseViewHolder(footerViews.get(viewType));
        }
        return onCreateItemViewHolder(parent, viewType);
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (isHeaderViewPosition(position) || isFooterViewPosition(position)) return;
        isScoll2Bottom = position+1 == getRealItemCount();
        onBindItemViewHolder((BaseViewHolder) holder, position - getHeaderSize());

    }

    /**
     * On create item view holder recycler view . view holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the recycler view . view holder
     */
    protected RecyclerView.ViewHolder onCreateItemViewHolder(final ViewGroup parent, int viewType)
    {
        return new BaseViewHolder<T>(parent);
    }


    /**
     * On bind item view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    public void onBindItemViewHolder(BaseViewHolder holder, final int position)
    {
        if (position >= getRealItemCount())
            return;
        holder.setData(list.get(position),position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (onItemLongClickListener == null)
                    return false;
                return onItemLongClickListener.onItemLongClick(list.get(position), position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(list.get(position), position);
            }
        });

    }


    /**
     * Gets item view type.
     *
     * @param position the position
     * @return the item view type
     */
    @Override
    public int getItemViewType(int position)
    {

        if (isHeaderViewPosition(position))
            return headerViews.keyAt(position);
        else if (isFooterViewPosition(position))
            return footerViews.keyAt(position - getHeaderSize() - getRealItemCount());
        return super.getItemViewType(position - getHeaderSize());
    }

    /**
     * On attached to recycler view.
     *
     * @param recyclerView the recycler view
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager instanceof GridLayoutManager)
        {
            final GridLayoutManager gm = (GridLayoutManager) manager;
            gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    int viewType = getItemViewType(position);
                    if (headerViews.get(viewType) != null)
                        return gm.getSpanCount();
                    if (footerViews.get(viewType) != null)
                        return gm.getSpanCount();
                    return 1;
                }
            });
            gm.setSpanCount(gm.getSpanCount());
        }
    }


    /**
     * On view attached to window.
     *
     * @param holder the holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPosition(position) || isFooterViewPosition(position))
        {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams)
            {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }


    /**
     * Is scoll 2 bottom boolean.
     *
     * @return the boolean
     */
    public boolean isScoll2Bottom()
    {
        return  isScoll2Bottom;
    }


    /**
     * Clear all.
     */
    public void clearAll()
    {
        if (list == null) return;
        list.clear();
    }

    /**
     * Add all.
     *
     * @param list the list
     */
    public void addAll(List<T> list)
    {
        if (this.list == null) return;
        this.list.addAll(list);
    }


    /**
     * Display image.
     *
     * @param url        the url
     * @param view       the view
     * @param rounded    the rounded
     * @param loddingImg the lodding img
     * @param failedImg  the failed img
     */
    protected void displayImage(String url, ImageView view, int rounded, @DrawableRes int loddingImg, @DrawableRes int failedImg)
    {
        if (!TextUtils.equals(String.valueOf(view.getTag()), url))
        {
//            LoadImageFactory.getInstance().displayImage(url, view, rounded, loddingImg, failedImg);
            view.setTag(url);
        }
    }
}
