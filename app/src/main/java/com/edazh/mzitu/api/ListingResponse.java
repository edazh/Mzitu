package com.edazh.mzitu.api;

import android.support.annotation.Nullable;

import com.edazh.mzitu.vo.Album;

import java.util.List;

import retrofit2.Response;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/25 0025
 * 描述：
 */

public class ListingResponse {
    public final int code;

    @Nullable
    public String nextLink;

    @Nullable
    public final String errorMessage;

    public ListingResponse(Throwable throwable) {
        code = 500;
        nextLink = null;
        errorMessage = throwable.getMessage();
    }

    public ListingResponse(Response response) {
        code = response.code();
        nextLink = null;
        errorMessage = null;
    }

    public boolean isSuccessful() {
        return code >= 200 && code <= 300;
    }
}
