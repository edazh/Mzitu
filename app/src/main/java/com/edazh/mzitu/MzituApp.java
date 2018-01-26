package com.edazh.mzitu;

import android.app.Application;

import com.edazh.mzitu.api.LiveDataCallAdapterFactory;
import com.edazh.mzitu.api.MzituService;
import com.edazh.mzitu.api.convert.BaseConvertFactory;
import com.edazh.mzitu.api.convert.BaseResponseConvert;
import com.edazh.mzitu.api.convert.AlbumResponseConvert;
import com.edazh.mzitu.db.MzituDatabase;
import com.edazh.mzitu.db.MzituRepository;

import retrofit2.Retrofit;

/**
 * Created by edazh on 2018/1/10 0010.
 */

public class MzituApp extends Application {
    private AppExecutors mAppExecutors;
    private MzituService mMzituService;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public MzituDatabase getDatabase() {
        return MzituDatabase.getInstance(this, mAppExecutors);
    }

    public MzituRepository getRepository() {
        return MzituRepository.getInstance(getDatabase(), getMzituService(), mAppExecutors);
    }

    public MzituService getMzituService() {
        if (mMzituService == null) {

            mMzituService = new Retrofit.Builder()
                    .baseUrl("http://www.mzitu.com/")
                    .addConverterFactory(new BaseConvertFactory() {
                        @Override
                        protected BaseResponseConvert responseConvert() {
                            return new AlbumResponseConvert();
                        }
                    })
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .build()
                    .create(MzituService.class);

        }
        return mMzituService;
    }

}
