package com.barbosa.resume.optimizer.service;

import com.barbosa.resume.optimizer.dto.ResumeRequest;
import com.barbosa.resume.optimizer.dto.ResumeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeOptimizerService {

    public ResumeResponse optimizeResume(ResumeRequest request) {

        // Processing simulation (Mock)
        String companyName = "Tech Corp (Mock)";
        int score = 85;
        List<String> missingKeywords = List.of("AWS", "Docker", "Unit Testing");
        String tips = "Your resume is good, but focus more on quantitative results.";

        return new ResumeResponse(companyName, score, missingKeywords, tips);
    }
}
