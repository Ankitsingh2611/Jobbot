package com.ankit.jobbot.service;

import com.ankit.jobbot.entity.Job;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScoringService {

    // keyword -> points
    private static final Map<String, Integer> POSITIVE = Map.of(
            "java", 10,
            "spring boot", 15,
            "jpa", 5,
            "hibernate", 5,
            "mysql", 5,
            "rest", 5,
            "aws", 3,
            "junior", 10,
            "fresher", 10
    );

    // keyword -> penalty
    private static final Map<String, Integer> NEGATIVE = Map.of(
            "mca", 20,
            "5+ years", 30,
            "7+ years", 40,
            "senior", 25,
            "lead", 25,
            "architect", 30
    );

    public int calculateScore(Job job) {
        String text = ((job.getTitle() == null ? "" : job.getTitle()) + " "
                + (job.getDescription() == null ? "" : job.getDescription())).toLowerCase();

        int score = 0;
        for (var entry : POSITIVE.entrySet()) {
            if (text.contains(entry.getKey())) score += entry.getValue();
        }
        for (var entry : NEGATIVE.entrySet()) {
            if (text.contains(entry.getKey())) score -= entry.getValue();
        }
        return score;
    }
}