package com.barbosa.resume.optimizer.dto;

import java.util.List;

public record ResumeResponse(
        String companyName,
        int matchPercentage,
        List<String> missingKeywords,
        String improvementTips
) {
}
