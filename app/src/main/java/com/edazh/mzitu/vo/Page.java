package com.edazh.mzitu.vo;

/**
 * 项目：Mzitu
 * 作者：edazh
 * 邮箱：edazh@qq.com
 * 时间：2018/1/23 0023
 * 描述：
 */
public class Page {
    private String name;
    private String link;
    private String nextLink;

    public Page(String name, String link, String nextLink) {
        this.name = name;
        this.link = link;
        this.nextLink = nextLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
