package com.edazh.mzitu.db;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.edazh.mzitu.AppExecutors;
import com.edazh.mzitu.api.ApiResponse;
import com.edazh.mzitu.api.MzituService;
import com.edazh.mzitu.vo.Main;
import com.edazh.mzitu.vo.Resource;

import java.util.List;

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

    public LiveData<Resource<Main>> loadMain() {
        return new NetworkBoundResource<Main, Main>(mExecutors) {
            @Override
            protected void saveCallResult(@NonNull Main item) {
                mDatabase.mainDao().insert(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Main>> createCall() {
                return mMzituService.getMain();
            }

            @Override
            protected boolean shouldFetch(@Nullable Main data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<Main> loadFromDb() {
                return mDatabase.mainDao().loadMain();
            }
        }.asLiveData();
    }

}
