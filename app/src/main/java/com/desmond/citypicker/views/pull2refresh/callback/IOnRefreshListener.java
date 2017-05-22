package com.desmond.citypicker.views.pull2refresh.callback;

/**
 * The interface On refresh listener.
 *
 * @Todo
 * @Author desmond
 * @Date 2016 /12/13
 * @Pacakge com.chinasoft.mengniu.view.pull2refresh
 */
public interface IOnRefreshListener
{
    /**
     * On refresh.
     */
    void onRefresh();

    /**
     * On load more.
     */
    void onLoadMore();
}
