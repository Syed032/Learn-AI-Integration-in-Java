package com.rahman.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReaderDemo {

    public static void main(String[] args) throws IOException {

        File file = new File(
                "src/main/resources/JavaProgramming.pdf");

        PDDocument document =
                Loader.loadPDF(file);

        PDFTextStripper stripper =
                new PDFTextStripper();

        String text =
                stripper.getText(document);

        System.out.println(text);

        document.close();
    }
}