package com.desmond.citypicker.views.pull2refresh;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 2015/12/19.
 */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder {


    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        onInitializeView();
    }

    public void onInitializeView() {

    }

    public <T extends View> T findViewById(@IdRes int resId) {
        return (T) itemView.findViewById(resId);
    }

    public void setData(final T object) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClick(object);
            }
        });
    }

    public void setData(final T object,int pos) {
        setData(object);
    }

    public void onItemViewClick(T object) {

    }


}
