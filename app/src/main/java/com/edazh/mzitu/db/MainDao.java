package com.edazh.mzitu.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.edazh.mzitu.vo.Main;

/**
 * Created by edazh on 2018/1/11 0011.
 */
@Dao
public interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Main main);


    @Query("select * from main")
    LiveData<Main> loadMain();
}
