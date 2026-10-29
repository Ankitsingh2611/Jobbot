package com.ankit.jobbot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String company;
    private String location;
    @Column(unique = true)
    private String link;
    @Column(length = 5000)
    private String description;
    private int score;
    private String status = "NEW"; // NEW, APPLIED, INTERVIEW, REJECTED
    private LocalDate appliedDate;
    private LocalDate followUpDate;
}
