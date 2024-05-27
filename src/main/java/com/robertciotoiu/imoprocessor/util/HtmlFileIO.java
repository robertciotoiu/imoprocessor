package com.robertciotoiu.imoprocessor.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class HtmlFileIO {
    public String readHtmlFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return null;
        }
    }

    public void writeHtmlFile(String path, String fileName, String content) {
        try {
            Files.writeString(Path.of(path,fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
