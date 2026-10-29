package com.ankit.jobbot.controller;

import com.ankit.jobbot.entity.Job;
import com.ankit.jobbot.repository.JobRepository;
import com.ankit.jobbot.service.ScoringService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobRepository jobRepository;
    private final ScoringService scoringService;

    public JobController(JobRepository jobRepository, ScoringService scoringService) {
        this.jobRepository = jobRepository;
        this.scoringService = scoringService;
    }

    @GetMapping
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    @GetMapping("/top")
    public List<Job> top() {
        return jobRepository.findAllByOrderByScoreDesc();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Job job) {
        if (jobRepository.existsByLink(job.getLink())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job already exists");
        }
        job.setScore(scoringService.calculateScore(job));
        return ResponseEntity.status(HttpStatus.CREATED).body(jobRepository.save(job));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestBody Map<String, String> body) {
        return jobRepository.findById(id).map(job -> {
            String status = body.get("status");
            job.setStatus(status);
            if ("APPLIED".equals(status)) {
                job.setAppliedDate(LocalDate.now());
                job.setFollowUpDate(LocalDate.now().plusDays(7));
            }
            return ResponseEntity.ok(jobRepository.save(job));
        }).orElse(ResponseEntity.notFound().build());
    }
}