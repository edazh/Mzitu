package com.edazh.mzitu.db;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.edazh.mzitu.AppExecutors;
import com.edazh.mzitu.vo.Main;

/**
 * Created by edazh on 2018/1/11 0011.
 */
@Database(entities = {Main.class}, version = 1)
public abstract class MzituDatabase extends RoomDatabase {

    private static MzituDatabase sInstance;

    public static final String DATABASE_NAME = "mzitu-db";

    public abstract MainDao mainDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MediatorLiveData<>();

    public static MzituDatabase getInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (MzituDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private void updateDatabaseCreated(Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreate();
        }
    }

    private void setDatabaseCreate() {
        mIsDatabaseCreated.postValue(true);
    }

    private static MzituDatabase buildDatabase(Context context, final AppExecutors executors) {
        return Room.databaseBuilder(context, MzituDatabase.class, DATABASE_NAME).build();
    }


}
