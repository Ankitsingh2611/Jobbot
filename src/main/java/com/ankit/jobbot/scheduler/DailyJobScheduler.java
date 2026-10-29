package com.ankit.jobbot.scheduler;

import com.ankit.jobbot.service.JobCollectorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyJobScheduler {

    private final JobCollectorService collector;

    public DailyJobScheduler(JobCollectorService collector) {
        this.collector = collector;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void run() {
        collector.collect("java");
    }
}