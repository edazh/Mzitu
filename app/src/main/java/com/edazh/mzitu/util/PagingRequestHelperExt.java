package com.edazh.mzitu.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.edazh.mzitu.db.NetworkState;

import java.util.concurrent.Executor;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/26 0026
 * 描述：
 */

public class PagingRequestHelperExt extends PagingRequestHelper {
    /**
     * Creates a new PagingRequestHelper with the given {@link Executor} which is used to run
     * retry actions.
     *
     * @param retryService The {@link Executor} that can run the retry actions.
     */
    public PagingRequestHelperExt(@NonNull Executor retryService) {
        super(retryService);
    }

    private String getErrorMessage(StatusReport report) {
        Throwable throwable = report.getErrorFor(RequestType.values()[0]);
        if (throwable != null) {
            return throwable.getMessage();
        }
        return null;
    }

    public LiveData<NetworkState> createStatusLiveData() {
        final MutableLiveData<NetworkState> state = new MutableLiveData<>();

        addListener(new Listener() {
            @Override
            public void onStatusChange(@NonNull StatusReport report) {
                if (report.hasRunning()) {
                    state.postValue(NetworkState.loading());
                } else if (report.hasError()) {
                    state.postValue(NetworkState.error(getErrorMessage(report)));
                } else {
                    state.postValue(NetworkState.success());
                }
            }
        });

        return state;
    }
}
