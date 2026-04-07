package com.hotelmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_data")
public class AIData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String promptQuery;

    @Column(columnDefinition = "TEXT")
    private String responseData;

    private LocalDateTime createdAt = LocalDateTime.now();

    public AIData() {}

    public AIData(String promptQuery, String responseData) {
        this.promptQuery = promptQuery;
        this.responseData = responseData;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPromptQuery() { return promptQuery; }
    public void setPromptQuery(String promptQuery) { this.promptQuery = promptQuery; }

    public String getResponseData() { return responseData; }
    public void setResponseData(String responseData) { this.responseData = responseData; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
