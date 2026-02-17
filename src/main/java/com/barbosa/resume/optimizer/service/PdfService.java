package com.barbosa.resume.optimizer.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service responsible for handling PDF file operations.
 */
@Service
public class PdfService {

    /**
     * Extracts plain text from a PDF file.
     *
     * @param file The PDF file uploaded by the user.
     * @return The extracted text content as a String.
     * @throws IllegalArgumentException if the file is empty or not a PDF.
     * @throws RuntimeException if the file cannot be processed.
     */
    public String extractTextFromPdf(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file is empty.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new IllegalArgumentException("Invalid file format. Only PDF files are supported.");
        }

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Error reading PDF file: " + e.getMessage(), e);
        }
    }
}
