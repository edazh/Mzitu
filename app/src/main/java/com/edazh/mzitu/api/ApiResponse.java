package com.edazh.mzitu.api;

import android.support.annotation.Nullable;

import retrofit2.Response;

/**
 * Created by edazh on 2018/1/10 0010.
 * Common class used by API responses.
 *
 * @param <T>
 */

public class ApiResponse<T> {
    public final int code;

    @Nullable
    public final T body;

    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        body = response.body();
        errorMessage = null;


    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}
