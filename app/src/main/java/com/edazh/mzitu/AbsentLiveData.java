package com.edazh.mzitu;

import android.arch.lifecycle.LiveData;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/22 0022
 * 描述：
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        //noinspection unchecked
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
