package com.barbosa.resume.optimizer.controller;

import com.barbosa.resume.optimizer.dto.ResumeRequest;
import com.barbosa.resume.optimizer.dto.ResumeResponse;
import com.barbosa.resume.optimizer.service.ResumeOptimizerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    private final ResumeOptimizerService resumeService;

    public ResumeController(ResumeOptimizerService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/optimize")
    public ResumeResponse optimize(@RequestBody ResumeRequest request) {
        return resumeService.optimizeResume(request);
    }
}