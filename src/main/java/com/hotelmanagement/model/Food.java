package com.hotelmanagement.model;

import jakarta.persistence.*;

/**
 * Model representing food items with regional discovery support.
 */
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String location;
    private String description;
    private Double price;
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    private Double rating;
    private String category;
    private String region;

    public Food() {}

    public Food(String title, String location, String description, Double price, String image, Double rating, String category, String region) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.price = price;
        this.image = image;
        this.rating = rating;
        this.category = category;
        this.region = region;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
