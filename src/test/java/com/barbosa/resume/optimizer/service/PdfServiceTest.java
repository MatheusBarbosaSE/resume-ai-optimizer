package com.barbosa.resume.optimizer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PdfService}.
 * Focuses on validating file integrity, format restrictions, and text extraction logic.
 */
class PdfServiceTest {

    private final PdfService pdfService = new PdfService();

    @Test
    @DisplayName("Should return extracted text when a valid PDF file is uploaded")
    void extractTextFromPdf_ShouldReturnText_WhenFileIsValid() throws IOException {
        // Arrange: Create a valid PDF document in memory
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Write content to the PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Hello Java World");
                contentStream.endText();
            }

            // Convert PDF to Byte Array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            // Create the MockMultipartFile with real PDF bytes
            MockMultipartFile validPdf = new MockMultipartFile(
                    "pdf",
                    "valid.pdf",
                    "application/pdf",
                    out.toByteArray()
            );

            // Act: Call the service
            String extractedText = pdfService.extractTextFromPdf(validPdf);

            // Assert: Check if the text was correctly extracted
            // Note: PDFBox might add line breaks or spaces, so we check using 'contains'
            assertNotNull(extractedText);
            assertTrue(extractedText.contains("Hello Java World"), "The extracted text should contain the content written to the PDF.");
        }
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when the uploaded file is empty")
    void extractTextFromPdf_ShouldThrowException_WhenFileIsEmpty() {
        // Arrange
        MockMultipartFile emptyFile = new MockMultipartFile(
                "pdf",
                "test.pdf",
                "application/pdf",
                new byte[0]
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pdfService.extractTextFromPdf(emptyFile);
        });

        assertEquals("The uploaded file is empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when the file format is not PDF")
    void extractTextFromPdf_ShouldThrowException_WhenFileIsNotPdf() {
        // Arrange
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "photo.png",
                "image/png",
                "fake-content".getBytes()
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pdfService.extractTextFromPdf(imageFile);
        });

        assertEquals("Invalid file format. Only PDF files are supported.", exception.getMessage());
    }
}
