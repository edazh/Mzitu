package com.edazh.mzitu.api;

import android.arch.lifecycle.LiveData;

import com.edazh.mzitu.vo.Main;
import com.edazh.mzitu.vo.Resource;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by edazh on 2018/1/10 0010.
 */

public interface MzituService {

    @GET("http://www.mzitu.com/")
    LiveData<ApiResponse<Main>> getMain();
}
