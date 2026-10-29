package com.ankit.jobbot.controller;

import com.ankit.jobbot.service.JobCollectorService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/collect")
public class CollectorController {

    private final JobCollectorService collector;

    public CollectorController(JobCollectorService collector) {
        this.collector = collector;
    }

    @PostMapping
    public Map<String, Object> collect(@RequestParam(defaultValue = "java") String keyword) {
        return Map.of("keyword", keyword, "newJobs", collector.collect(keyword));
    }
}