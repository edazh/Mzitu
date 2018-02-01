package com.edazh.mzitu.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/26 0026
 * 描述：
 */

public class NetworkState {
    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    private NetworkState(@NonNull Status status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public static NetworkState success() {
        return new NetworkState(Status.SUCCESS, null);
    }

    public static NetworkState loading() {
        return new NetworkState(Status.LOADING, null);
    }

    public static NetworkState error(String msg) {
        return new NetworkState(Status.ERROR, msg);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        NetworkState state = (NetworkState) obj;
        if (status != state.status) {
            return false;
        }
        return message != null ? message.equals(state.message) : state.message == null;
    }
}
