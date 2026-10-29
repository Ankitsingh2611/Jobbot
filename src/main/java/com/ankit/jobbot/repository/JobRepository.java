package com.ankit.jobbot.repository;

import com.ankit.jobbot.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByLink(String link);

    List<Job> findAllByOrderByScoreDesc();
}