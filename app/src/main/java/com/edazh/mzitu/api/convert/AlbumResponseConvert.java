package com.edazh.mzitu.api.convert;

import com.edazh.mzitu.vo.Album;
import com.edazh.mzitu.vo.Page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edazh on 2018/1/11 0011.
 */

public class AlbumResponseConvert extends BaseResponseConvert<List<Album>> {
    @Override
    protected List<Album> parserHtml(String html) {

        Document document = Jsoup.parse(html);
        String pageName = document.selectFirst("li.current-menu-item a").attr("title");
        String pageNextLink = document.selectFirst("a.next.page-numbers").attr("href").split("/")[4];
        String pageLink = String.valueOf(Integer.parseInt(pageNextLink) - 1);
        Page page = new Page(pageName, pageLink, pageNextLink);

        Elements albumElements = document.select(".postlist li");

        List<Album> albumList = new ArrayList<>();

        for (Element element : albumElements) {
            String title = element.selectFirst("img.lazy").attr("alt");
            String coverLink = element.selectFirst("img.lazy").attr("data-original");
            String time = element.selectFirst("span.time").text();
            String view = element.selectFirst("span.view").text();
            String link = element.selectFirst("a").attr("href");
            String id = link.split("/")[3];

            albumList.add(new Album(id, title, link, coverLink, page, time, view));

        }
        return albumList;
    }
}
