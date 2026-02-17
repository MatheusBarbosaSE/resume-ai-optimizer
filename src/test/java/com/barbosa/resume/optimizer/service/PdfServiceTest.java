package com.barbosa.resume.optimizer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link PdfService}.
 * Focuses on validating file integrity and format restrictions.
 */
class PdfServiceTest {

    // Service under test
    private final PdfService pdfService = new PdfService();

    @Test
    @DisplayName("Should throw IllegalArgumentException when the uploaded file is empty")
    void extractTextFromPdf_ShouldThrowException_WhenFileIsEmpty() {
        // Arrange: Create an empty dummy file
        MockMultipartFile emptyFile = new MockMultipartFile(
                "pdf",
                "test.pdf",
                "application/pdf",
                new byte[0]
        );

        // Act & Assert: Verify that the service throws the expected exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pdfService.extractTextFromPdf(emptyFile);
        });

        // Assert: Verify the exception message
        assertEquals("The uploaded file is empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when the file format is not PDF")
    void extractTextFromPdf_ShouldThrowException_WhenFileIsNotPdf() {
        // Arrange: Create a dummy file with an image content type
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "photo.png",
                "image/png",
                "fake-content".getBytes()
        );

        // Act & Assert: Verify that the service throws the expected exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pdfService.extractTextFromPdf(imageFile);
        });

        // Assert: Verify the exception message
        assertEquals("Invalid file format. Only PDF files are supported.", exception.getMessage());
    }
}
