package com.edazh.mzitu.db;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.MainThread;

import com.edazh.mzitu.AppExecutors;
import com.edazh.mzitu.api.MzituService;
import com.edazh.mzitu.ui.callback.RefreshCallback;
import com.edazh.mzitu.ui.callback.RetryCallback;
import com.edazh.mzitu.vo.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edazh on 2018/1/11 0011.
 */

public class MzituRepository {
    private static MzituRepository sInstance;

    private final MzituDatabase mDatabase;

    private final MzituService mMzituService;

    private final AppExecutors mExecutors;

    private MzituRepository(MzituDatabase database, MzituService mzituService, AppExecutors executors) {
        mDatabase = database;
        mMzituService = mzituService;
        mExecutors = executors;
    }

    public static MzituRepository getInstance(MzituDatabase database, MzituService mzituService, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (MzituRepository.class) {
                if (sInstance == null) {
                    sInstance = new MzituRepository(database, mzituService, executors);
                }
            }
        }
        return sInstance;
    }

    @MainThread
    public Listing<Album> loadAlbums(final String pageName) {
        DataSource.Factory<Integer, Album> factory = mDatabase.albumDao().loadByPageNameAndLink(pageName);
        final AlbumBoundaryCallback albumBoundaryCallback = new AlbumBoundaryCallback(mExecutors, mDatabase.albumDao(), mMzituService);
        LiveData<PagedList<Album>> pagedList = new LivePagedListBuilder<Integer, Album>(factory, new PagedList.Config.Builder()
                .setPrefetchDistance(24)
                .setEnablePlaceholders(true)
                .setPageSize(24)
                .build())
                .setBoundaryCallback(albumBoundaryCallback)
                .setBackgroundThreadExecutor(mExecutors.networkIO())
                .build();

        final MutableLiveData<Void> refreshTrigger = new MutableLiveData<>();
        LiveData<NetworkState> refreshState = Transformations.switchMap(refreshTrigger, new Function<Void, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(Void input) {
                return refresh(pageName);
            }
        });

        return new Listing<Album>(pagedList,
                albumBoundaryCallback.getNetworkState(),
                refreshState,
                new RefreshCallback() {
                    @Override
                    public void onRefresh() {
                        refreshTrigger.setValue(null);
                    }
                },
                new RetryCallback() {
                    @Override
                    public void onRetry() {
                        albumBoundaryCallback.getPagingRequestHelper().retryAllFailed();
                    }
                }
        );

    }

    @MainThread
    public LiveData<NetworkState> refresh(final String pageName) {
        final MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
        networkState.setValue(NetworkState.loading());
        mMzituService.getAlbumOfMainPage("1").enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, final Response<List<Album>> response) {
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.runInTransaction(new Runnable() {
                            @Override
                            public void run() {
                                mDatabase.albumDao().deleteAlbumsByPageName(pageName);
                                mDatabase.albumDao().insertAll(response.body());
                            }
                        });
                        networkState.postValue(NetworkState.success());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                networkState.setValue(NetworkState.error(t.getMessage()));
            }
        });

        return networkState;
    }

}
