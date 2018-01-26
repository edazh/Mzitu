package com.edazh.mzitu.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.edazh.mzitu.ui.callback.RefreshCallback;
import com.edazh.mzitu.ui.callback.RetryCallback;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/26 0026
 * 描述：
 */

public class Listing<T> {
    public final LiveData<PagedList<T>> pagedList;
    public final LiveData<NetworkState> networkState;
    public final LiveData<NetworkState> refreshState;
    public final RefreshCallback refreshCallback;
    public final RetryCallback retryCallback;

    public Listing(LiveData<PagedList<T>> pagedList, LiveData<NetworkState> networkState, LiveData<NetworkState> refreshState, RefreshCallback refreshCallback, RetryCallback retryCallback) {
        this.pagedList = pagedList;
        this.networkState = networkState;
        this.refreshState = refreshState;
        this.refreshCallback = refreshCallback;
        this.retryCallback = retryCallback;
    }
}
