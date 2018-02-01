package com.edazh.mzitu.api;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.edazh.mzitu.db.Listing;
import com.edazh.mzitu.vo.Album;
import com.edazh.mzitu.vo.MM;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by edazh on 2018/1/10 0010.
 * e-mail:edazh@qq.com
 */

public interface MzituService {

    @GET("all/")
    LiveData<ApiResponse<List<Album>>> getAll();

    @GET("page/{page}/")
    Call<List<Album>> getAlbumOfMainPage(@Path("page") String pageLink);

    @GET("{type}/page/{page}")
    LiveData<ApiResponse<List<Album>>> getAlbumOfPage(@Path("type") String albumType, @Path("page") String page);

    @GET("{id}")
    LiveData<ApiResponse<List<MM>>> getMM(@Path("id") String albumId);
}
