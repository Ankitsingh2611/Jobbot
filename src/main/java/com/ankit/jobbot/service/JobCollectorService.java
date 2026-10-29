package com.ankit.jobbot.service;

import com.ankit.jobbot.entity.Job;
import com.ankit.jobbot.repository.JobRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class JobCollectorService {

    private final JobRepository jobRepository;
    private final ScoringService scoringService;
    private final RestClient restClient = RestClient.create();

    public JobCollectorService(JobRepository jobRepository, ScoringService scoringService) {
        this.jobRepository = jobRepository;
        this.scoringService = scoringService;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RemotiveJob(
            String url,
            String title,
            @JsonProperty("company_name") String companyName,
            @JsonProperty("candidate_required_location") String location,
            String description) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RemotiveResponse(List<RemotiveJob> jobs) {}

    public int collect(String keyword) {
        RemotiveResponse response = restClient.get()
                .uri("https://remotive.com/api/remote-jobs?search={q}", keyword)
                .retrieve()
                .body(RemotiveResponse.class);

        if (response == null || response.jobs() == null) return 0;

        int saved = 0;
        for (RemotiveJob r : response.jobs()) {
            if (r.url() == null || jobRepository.existsByLink(r.url())) continue;

            Job job = new Job();
            job.setTitle(r.title());
            job.setCompany(r.companyName());
            job.setLocation(r.location());
            job.setLink(r.url());
            job.setDescription(clean(r.description()));
            job.setScore(scoringService.calculateScore(job));
            jobRepository.save(job);
            saved++;
        }
        return saved;
    }

    // strip HTML tags and cut to the column limit (5000)
    private String clean(String html) {
        if (html == null) return "";
        String text = html.replaceAll("<[^>]*>", " ").replaceAll("\\s+", " ").trim();
        return text.length() > 5000 ? text.substring(0, 5000) : text;
    }
}