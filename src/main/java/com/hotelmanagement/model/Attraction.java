package com.hotelmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tours")
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    private String title;
    private String location;
    private String duration;
    private Double rating;
    private String category;
    private String region;
    private String bestSeason;
    
    @Column(length = 1000)
    private String description;

    private String guideEmail;

    private boolean approved = false;

    public Attraction() {}

    public Attraction(String image, String title, String location, String duration, Double rating, String category, String description, String region, String bestSeason) {
        this.image = image;
        this.title = title;
        this.location = location;
        this.duration = duration;
        this.rating = rating;
        this.category = category;
        this.description = description;
        this.region = region;
        this.bestSeason = bestSeason;
        this.approved = false; // Default to false for manual submissions
    }

    public Attraction(String image, String title, String location, String duration, Double rating, String category, String description, String region, String bestSeason, boolean approved) {
        this(image, title, location, duration, rating, category, description, region, bestSeason);
        this.approved = approved;
    }

    // Primitive Double Overload for IDE compatibility
    public Attraction(String image, String title, String location, String duration, double rating, String category, String description, String region, String bestSeason) {
        this(image, title, location, duration, Double.valueOf(rating), category, description, region, bestSeason);
    }

    // 7-arg Legacy Overload
    public Attraction(String image, String title, String location, String duration, Double rating, String category, String description) {
        this(image, title, location, duration, rating, category, description, "Unknown", "Year-round");
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getBestSeason() { return bestSeason; }
    public void setBestSeason(String bestSeason) { this.bestSeason = bestSeason; }

    public String getGuideEmail() { return guideEmail; }
    public void setGuideEmail(String guideEmail) { this.guideEmail = guideEmail; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
