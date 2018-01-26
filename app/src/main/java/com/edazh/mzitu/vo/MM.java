package com.edazh.mzitu.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by edazh on 2018/1/14 0014.
 * e-mail:edazh@qq.com
 */
@Entity
public class MM {
    @PrimaryKey
    private String id;
    @PrimaryKey
    @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "albumId", onUpdate = ForeignKey.CASCADE)
    private String albumId;

    private String link;

    private String imageLink;

    private String nextLink;

    public MM(String id, String albumId, String link, String imageLink, String nextLink) {
        this.id = id;
        this.albumId = albumId;
        this.link = link;
        this.imageLink = imageLink;
        this.nextLink = nextLink;
    }

    @Ignore
    public MM() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }
}
