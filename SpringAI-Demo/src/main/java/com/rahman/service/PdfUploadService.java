package com.rahman.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfUploadService {

    private final VectorStore vectorStore;

    public PdfUploadService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void uploadPdf(MultipartFile file) throws IOException {

        try (PDDocument pdf = Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(pdf);

            if (text.isBlank()) {
                throw new IllegalArgumentException("Uploaded PDF is empty.");
            }

            Document document = new Document(
                    text,
                    Map.of("source", file.getOriginalFilename()));

            TokenTextSplitter splitter = new TokenTextSplitter();

            List<Document> chunks = splitter.split(document);

            vectorStore.add(chunks);

            System.out.println("Uploaded PDF : " + file.getOriginalFilename());
            System.out.println("Chunks Added : " + chunks.size());
        }
    }
}