package com.edazh.mzitu.vo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by edazh on 2018/1/14 0014.
 * e-mail:edazh@qq.com
 */
@Entity
public class Album {
    @NonNull
    @PrimaryKey
    private String id;

    private String title;
    private String link;
    private String coverLink;
    private String category;
    private String time;
    private String view;
    @Embedded(prefix = "page_")
    private Page page;

    public Album(@NonNull String id, String title, String link, String coverLink, Page page, String time, String view) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.coverLink = coverLink;
        this.page = page;
        this.time = time;
        this.view = view;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
