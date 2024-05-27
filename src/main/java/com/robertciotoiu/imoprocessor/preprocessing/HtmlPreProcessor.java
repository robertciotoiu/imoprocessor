package com.robertciotoiu.imoprocessor.preprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HtmlPreProcessor {
    HtmlCleaner htmlCleaner;

    public HtmlPreProcessor(@Autowired HtmlCleaner htmlCleaner){
        this.htmlCleaner = htmlCleaner;
    }

    public String preProcess(String html){
        return htmlCleaner.clean(html);
    }
}
