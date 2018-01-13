package com.edazh.mzitu.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by edazh on 2018/1/10 0010.
 */
@Entity(primaryKeys = "src")
public class Main {
    public final String title;
    @NonNull
    public final String src;
    public final String album;
    public final String time;
    public final String view;

    public Main(String title, String src, String album, String time, String view) {
        this.title = title;
        this.src = src;
        this.album = album;
        this.time = time;
        this.view = view;
    }
}
