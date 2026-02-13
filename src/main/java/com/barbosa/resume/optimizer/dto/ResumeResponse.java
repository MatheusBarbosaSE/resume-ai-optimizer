package com.barbosa.resume.optimizer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ResumeResponse(
        @JsonProperty("targetJobCompanyName")
        String companyName,

        int matchPercentage,
        List<String> missingKeywords,
        String improvementTips
) {
}
