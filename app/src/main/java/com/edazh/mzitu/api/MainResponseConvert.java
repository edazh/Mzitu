package com.edazh.mzitu.api;

import com.edazh.mzitu.vo.Main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edazh on 2018/1/11 0011.
 */

public class MainResponseConvert extends BaseResponseConvert<List<Main>> {
    @Override
    protected List<Main> parserHtml(String html) {
        List<Main> mains = new ArrayList<>();

        Document document = Jsoup.parse(html);

        Elements postList = document.select("div.postlist").select("li");

        for (Element element : postList) {
            String title = element.select("img.lazy").first().attr("alt");
            String src = element.select("img.lazy").first().attr("src");
            String album = element.select("li a").first().attr("href");
            String time = element.select("span.time").first().text();
            String view = element.select("span.view").first().text();

            Main main = new Main(title, src, album, time, view);

            mains.add(main);
        }

        return mains;
    }
}
