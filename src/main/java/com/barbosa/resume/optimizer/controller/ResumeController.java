package com.barbosa.resume.optimizer.controller;

import com.barbosa.resume.optimizer.dto.ResumeRequest;
import com.barbosa.resume.optimizer.dto.ResumeResponse;
import com.barbosa.resume.optimizer.service.PdfService;
import com.barbosa.resume.optimizer.service.ResumeOptimizerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller for handling resume optimization requests.
 */
@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    private final ResumeOptimizerService resumeService;
    private final PdfService pdfService;

    // Constructor-based Dependency Injection
    public ResumeController(ResumeOptimizerService resumeService, PdfService pdfService) {
        this.resumeService = resumeService;
        this.pdfService = pdfService;
    }

    /**
     * Endpoint to upload a PDF resume and a job description for AI analysis.
     *
     * @param pdf The resume file uploaded by the user.
     * @param jobDescription The description of the job the user is applying for.
     * @return AI analysis response containing match score and tips.
     */
    @PostMapping("/optimize")
    public ResumeResponse optimize(
            @RequestParam("pdf") MultipartFile pdf,
            @RequestParam("jobDescription") String jobDescription) {

        // 1. Extract the text from the uploaded PDF
        String extractedText = pdfService.extractTextFromPdf(pdf);

        // 2. Create the request object
        ResumeRequest myRequest = new ResumeRequest(extractedText, jobDescription);

        // 3. Delegate to the AI service
        return resumeService.optimizeResume(myRequest);
    }
}
