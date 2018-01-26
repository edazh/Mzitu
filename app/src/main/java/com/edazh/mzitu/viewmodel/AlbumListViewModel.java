package com.edazh.mzitu.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.edazh.mzitu.MzituApp;
import com.edazh.mzitu.db.Listing;
import com.edazh.mzitu.db.MzituRepository;
import com.edazh.mzitu.db.NetworkState;
import com.edazh.mzitu.vo.Album;

/**
 * Created by edazh on 2018/1/14 0014.
 * e-mail:edazh@qq.com
 */

public class AlbumListViewModel extends AndroidViewModel {
    private final MutableLiveData<String> pageName = new MutableLiveData<>();
    private final LiveData<Listing<Album>> mListingLiveData;
    private final MzituRepository mRepository;

    public final LiveData<PagedList<Album>> mAlbumListLiveData;

    public final LiveData<NetworkState> mNetworkStateLiveData;

    public final LiveData<NetworkState> mRefreshStateLiveData;

    public AlbumListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((MzituApp) application).getRepository();

        mListingLiveData = Transformations.map(pageName, new Function<String, Listing<Album>>() {
            @Override
            public Listing<Album> apply(String input) {
                return mRepository.loadAlbums(input);
            }
        });

        mAlbumListLiveData = Transformations.switchMap(mListingLiveData, new Function<Listing<Album>, LiveData<PagedList<Album>>>() {
            @Override
            public LiveData<PagedList<Album>> apply(Listing<Album> input) {
                return input.pagedList;
            }
        });

        mNetworkStateLiveData = Transformations.switchMap(mListingLiveData, new Function<Listing<Album>, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(Listing<Album> input) {
                return input.networkState;
            }
        });


        mRefreshStateLiveData = Transformations.switchMap(mListingLiveData, new Function<Listing<Album>, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(Listing<Album> input) {
                return input.refreshState;
            }
        });
    }

    public boolean showPage(String pageName) {
        if (this.pageName.getValue() == pageName) {
            return false;
        }
        this.pageName.setValue(pageName);
        return true;
    }

    public void refresh() {
        if (mListingLiveData.getValue() != null) {
            mListingLiveData.getValue().refreshCallback.onRefresh();
        }
    }

    public void retry() {
        Listing<Album> listing = mListingLiveData.getValue();
        if (listing != null) {
            listing.retryCallback.onRetry();
        }
    }

}
