package com.robertciotoiu.imoprocessor;

import com.robertciotoiu.imoprocessor.preprocessing.HtmlPreProcessor;
import com.robertciotoiu.imoprocessor.util.HtmlFileIO;
import com.robertciotoiu.imoprocessor.util.ProcessedHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImoProcessorService {
    @Value("${html.folder.path}")
    private String htmlFolderPath;
    @Value("${processed.output.folder.path}")
    private String outputFolderPath;
    private final HtmlPreProcessor htmlPreProcessor;
    private final HtmlFileIO htmlFileIO;

    public ImoProcessorService(@Autowired HtmlPreProcessor htmlPreProcessor, @Autowired HtmlFileIO htmlFileIO) {
        this.htmlPreProcessor = htmlPreProcessor;
        this.htmlFileIO = htmlFileIO;
    }

    /**
     * Method that will process an entire folder of HTMLS
     */
    @EventListener(ApplicationReadyEvent.class)
    public void process() {
        try (var files = Files.list(Path.of(htmlFolderPath))) {
            files
                    .filter(Files::isRegularFile)
                    .map(path -> new ProcessedHtml(path.getFileName().toString(), htmlFileIO.readHtmlFile(path)))
                    .filter(processedHtml -> processedHtml.content() != null)
                    .map(processedHtml -> new ProcessedHtml(processedHtml.fileName(), htmlPreProcessor.preProcess(processedHtml.content())))
                    .forEach(processedHtml -> htmlFileIO.writeHtmlFile(outputFolderPath, processedHtml.fileName(), processedHtml.content()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
