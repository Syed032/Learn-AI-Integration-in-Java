package com.rahman.rag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    private final VectorStore vectorStore;

    public DataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadData() throws IOException {

        List<Document> documents = new ArrayList<>();

        Path resourceFolder = Paths.get("src/main/resources");

        Files.list(resourceFolder)
                .filter(Files::isRegularFile)
                .forEach(path -> {

                    try {

                        String fileName = path.getFileName().toString();

                        if (fileName.endsWith(".txt")) {

                            String content = Files.readString(path);

                            if (!content.isBlank()) {
                                documents.add(new Document(content,
                                        Map.of("source", fileName)));

                                System.out.println("TXT Loaded : " + fileName);
                            }
                        }

                        else if (fileName.endsWith(".pdf")) {

                            try (PDDocument pdf = Loader.loadPDF(path.toFile())) {

                                PDFTextStripper stripper = new PDFTextStripper();

                                String text = stripper.getText(pdf);

                                if (!text.isBlank()) {
                                    documents.add(new Document(text,
                                            Map.of("source", fileName)));

                                    System.out.println("PDF Loaded : " + fileName);
                                }
                            }
                        }

                    } catch (Exception e) {
                        System.err.println("Failed to load : " + path.getFileName());
                        e.printStackTrace();
                    }

                });

        System.out.println("Total Documents : " + documents.size());

        TokenTextSplitter splitter = new TokenTextSplitter();

        List<Document> chunks = documents.stream()
                .flatMap(document -> splitter.split(document).stream())
                .toList();

        System.out.println("Total Chunks : " + chunks.size());

        vectorStore.add(chunks);

        System.out.println("Knowledge Loaded Successfully");
    }
}