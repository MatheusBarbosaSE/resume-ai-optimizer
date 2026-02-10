package com.barbosa.resume.optimizer.service;

import com.barbosa.resume.optimizer.dto.ResumeRequest;
import com.barbosa.resume.optimizer.dto.ResumeResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Service responsible for interacting with the AI model to analyze resumes.
 */
@Service
public class ResumeOptimizerService {

    private final ChatClient chatClient;

    public ResumeOptimizerService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Optimizes the resume based on the job description using AI.
     *
     * @param request The request containing the resume and job description.
     * @return A structured response with score and improvements.
     */
    public ResumeResponse optimizeResume(ResumeRequest request) {
        // Prompt for the LLM
        String promptUser = """
            You are an expert IT Recruiter and Resume Writer. Analyze the following resume against the job description.
            
            Job Description: %s
            Resume Content: %s
            
            Please provide a strict JSON response (no markdown, no explanations) following exactly this structure:
                \\{
                "companyName": "Company Name (if found in job description, otherwise 'Unknown')",
                "matchPercentage": 85,
                "missingKeywords": ["list", "of", "missing", "technical", "skills"],
                "improvementTips": "Concise and actionable tips to improve the resume for this specific job."
                \\}
            """.formatted(request.jobDescription(), request.resumeContent());

        // Call the AI model and map the output to the DTO
        return chatClient.prompt()
                .user(promptUser)
                .call()
                .entity(ResumeResponse.class);
    }
}
