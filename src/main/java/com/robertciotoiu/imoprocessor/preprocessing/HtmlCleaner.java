package com.robertciotoiu.imoprocessor.preprocessing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HtmlCleaner {
    private final List<String> tagsToRemove = List.of("input", "script", "style", "iframe", "link", "meta", "head","symbol","svg");

    public String clean(String html){
        return removeUselessTags(html);
    }
    private String removeUselessTags(String html){
        // Parse the HTML content
        Document doc = Jsoup.parse(html);

        // Remove unwanted tags
        for (String tag : tagsToRemove) {
            Elements elements = doc.select(tag);
            for (Element element : elements) {
                element.remove();
            }
        }

        // Output cleaned HTML
        return doc.html();
    }
}
