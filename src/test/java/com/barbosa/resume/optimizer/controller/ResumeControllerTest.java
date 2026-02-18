package com.barbosa.resume.optimizer.controller;

import com.barbosa.resume.optimizer.dto.ResumeRequest;
import com.barbosa.resume.optimizer.dto.ResumeResponse;
import com.barbosa.resume.optimizer.service.PdfService;
import com.barbosa.resume.optimizer.service.ResumeOptimizerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link ResumeController} using MockMvc.
 * Validates HTTP endpoints and service integration without loading the full context.
 */
@WebMvcTest(ResumeController.class)
class ResumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResumeOptimizerService resumeService;

    @MockitoBean
    private PdfService pdfService;

    @Test
    @DisplayName("Should return 200 OK and optimized resume when valid PDF is uploaded")
    void optimize_ShouldReturnResumeResponse_WhenRequestIsValid() throws Exception {
        // Arrange: Prepare mock data and behavior
        MockMultipartFile pdfFile = new MockMultipartFile(
                "pdf",
                "resume.pdf",
                "application/pdf",
                "Dummy PDF Content".getBytes()
        );

        String jobDescription = "We need a Java Developer.";
        String extractedText = "My Resume Text";

        // Prepare the expected response from AI service
        ResumeResponse expectedResponse = new ResumeResponse(
                "Tech Corp",
                95,
                List.of("Docker", "Kubernetes"),
                "Learn Containerization."
        );

        // Define Mock Behavior (Stubbing)
        given(pdfService.extractTextFromPdf(any())).willReturn(extractedText);
        given(resumeService.optimizeResume(any(ResumeRequest.class))).willReturn(expectedResponse);

        // Act & Assert: Perform the HTTP request and verify results
        mockMvc.perform(multipart("/api/v1/resume/optimize")
                        .file(pdfFile)
                        .param("jobDescription", jobDescription)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(jsonPath("$.targetJobCompanyName").value("Tech Corp"))
                .andExpect(jsonPath("$.matchPercentage").value(95))
                .andExpect(jsonPath("$.missingKeywords[0]").value("Docker"))
                .andExpect(jsonPath("$.improvementTips").value("Learn Containerization."));
    }
}
