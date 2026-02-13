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
            You are an expert IT Recruiter. Compare the RESUME against the JOB DESCRIPTION.
        
            JOB DESCRIPTION:
            ---
            %s
            ---
            
            RESUME:
            ---
            %s
            ---
            
            EVALUATION INSTRUCTIONS:
            1. targetJobCompanyName: Extract the hiring company's name ONLY from the JOB DESCRIPTION. If it is not explicitly mentioned in the job description, you MUST return the exact word "Unknown".
            2. matchPercentage: Calculate a real integer score from 0 to 100 representing how well the resume fits the job requirements.
            3. missingKeywords: List technical skills required by the job that are COMPLETELY ABSENT from the resume. Use logical deduction (e.g., if the job asks for "Cloud Computing", and the resume has "AWS", it is NOT missing. If the job asks for "Spring", and the resume has "Spring Boot", it is NOT missing).
            4. improvementTips: Give one short, actionable tip to improve the resume based on the missing keywords.
            """.formatted(request.jobDescription(), request.resumeContent());

        // Call the AI model and map the output to the DTO
        return chatClient.prompt()
                .user(promptUser)
                .call()
                .entity(ResumeResponse.class);
    }
}
