package com.hotelmanagement.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "homestays")
public class Homestay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    private String title;
    private String location;
    private Double rating;
    private Double price;
    private String host;
    private Integer guests;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer maxCapacity = 8;
    
    @Column(length = 2000)
    private String description;

    @ElementCollection
    @OrderColumn(name = "amenity_order")
    private List<String> amenities;

    private String category;
    private String region;
    private String bestSeason;
    private Boolean approved = true;

    public Homestay() {
        this.maxCapacity = 8;
    }

    public Homestay(String image, String title, String location, Double rating, Double price, String host, Integer guests, Integer bedrooms, Integer bathrooms, List<String> amenities, String category, Integer maxCapacity, String description, String region, String bestSeason) {
        this.image = image;
        this.title = title;
        this.location = location;
        this.rating = rating;
        this.price = price;
        this.host = host;
        this.guests = guests;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.amenities = amenities;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.description = description;
        this.region = region;
        this.bestSeason = bestSeason;
    }

    // 14-arg legacy/ghost overload (as reported in IDE)
    public Homestay(String image, String title, String location, double rating, double price, String host, int guests, int bedrooms, int bathrooms, List<String> amenities, String category, int maxCapacity, String description, String region) {
        this(image, title, location, rating, price, host, guests, bedrooms, bathrooms, amenities, category, maxCapacity, description, region, "Year-round");
    }

    // 13-arg legacy overload
    public Homestay(String image, String title, String location, Double rating, Double price, String host, Integer guests, Integer bedrooms, Integer bathrooms, List<String> amenities, String category, Integer maxCapacity, String description) {
        this(image, title, location, rating, price, host, guests, bedrooms, bathrooms, amenities, category, maxCapacity, description, "Unknown", "Year-round");
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

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public Integer getGuests() { return guests; }
    public void setGuests(Integer guests) { this.guests = guests; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }

    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getBestSeason() { return bestSeason; }
    public void setBestSeason(String bestSeason) { this.bestSeason = bestSeason; }

    public Boolean getApproved() { return approved; }
    public void setApproved(Boolean approved) { this.approved = approved; }
}
