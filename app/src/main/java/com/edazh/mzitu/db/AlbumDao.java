package com.edazh.mzitu.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.edazh.mzitu.vo.Album;

import java.util.List;

/**
 * Created by edazh on 2018/1/14 0014.
 * e-mail:edazh@qq.com
 */
@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Album> albums);

    @Query("delete from album where page_name = :pageName")
    void deleteAlbumsByPageName(String pageName);

    @Query("select * from album where page_name = :pageName order by id desc")
    DataSource.Factory<Integer, Album> loadByPageNameAndLink(String pageName);
}
