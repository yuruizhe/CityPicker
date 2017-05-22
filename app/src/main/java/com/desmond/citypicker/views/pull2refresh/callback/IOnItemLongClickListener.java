package com.desmond.citypicker.views.pull2refresh.callback;

/**
 * The interface On item long click listener.
 *
 * @param <T> the type parameter
 * @Todo
 * @Author desmond
 * @Date 2016 /12/15
 * @Pacakge com.chinasoft.mengniu.view.pull2refresh
 */
public interface IOnItemLongClickListener<T>
{
    /**
     * On item long click boolean.
     *
     * @param obj      the obj
     * @param position the position
     * @return the boolean
     */
    boolean onItemLongClick(T obj, int position);
}
