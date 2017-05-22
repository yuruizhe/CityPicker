package com.desmond.citypicker.views.pull2refresh.callback;

/**
 * The interface On item click listener.
 *
 * @param <T> the type parameter
 * @Todo
 * @Author desmond
 * @Date 2016 /12/14
 * @Pacakge com.chinasoft.mengniu.view.pull2refresh
 */
public interface IOnItemClickListener<T>
{
    /**
     * On item click.
     *
     * @param obj      the obj
     * @param position the position
     */
    void onItemClick(T obj, int position);
}
