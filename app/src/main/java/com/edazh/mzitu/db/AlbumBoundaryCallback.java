package com.edazh.mzitu.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.edazh.mzitu.AppExecutors;
import com.edazh.mzitu.api.MzituService;
import com.edazh.mzitu.util.PagingRequestHelper;
import com.edazh.mzitu.util.PagingRequestHelperExt;
import com.edazh.mzitu.vo.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/25 0025
 * 描述：
 */

public class AlbumBoundaryCallback extends PagedList.BoundaryCallback<Album> {
    private static final String TAG = "AlbumBoundaryCallback";
    private final AppExecutors mExecutors;
    private final AlbumDao mAlbumDao;
    private final MzituService mService;

    private final PagingRequestHelperExt mHelper;

    private final LiveData<NetworkState> networkState;

    public AlbumBoundaryCallback(AppExecutors executors, AlbumDao albumDao, MzituService service) {
        mExecutors = executors;
        mAlbumDao = albumDao;
        mService = service;
        mHelper = new PagingRequestHelperExt(executors.diskIO());
        networkState = mHelper.createStatusLiveData();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();

        mHelper.runIfNotRunning(PagingRequestHelperExt.RequestType.INITIAL, new PagingRequestHelper.Request() {
            @Override
            public void run(Callback callback) {
                mService.getAlbumOfMainPage("1").enqueue(createWebserviceCallback(callback));
            }
        });

    }

    @Override
    public void onItemAtEndLoaded(@NonNull final Album itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);

        mHelper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER, new PagingRequestHelper.Request() {
            @Override
            public void run(Callback callback) {
                mService.getAlbumOfMainPage(itemAtEnd.getPage().getNextLink()).enqueue(createWebserviceCallback(callback));
            }
        });
    }


    private Callback<List<Album>> createWebserviceCallback(final PagingRequestHelper.Request.Callback callback) {
        return new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, final Response<List<Album>> response) {
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mAlbumDao.insertAll(response.body());
                        callback.recordSuccess();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                callback.recordFailure(t);
            }
        };
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public PagingRequestHelper getPagingRequestHelper() {
        return mHelper;
    }
}
