package com.hotelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private Double price;
    private Double rating;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String region;
    @Column(name = "best_season")
    private String bestSeason;
    
    @Column(name = "hotel_capacity_limit")
    private int hotelCapacityLimit = 0;

    public Hotel() {
    }

    public Hotel(String name, String location, Double price, Double rating) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.rating = rating;
        this.hotelCapacityLimit = 2; // Default
    }

    public Hotel(String name, String location, Double price, Double rating, String image, String region, String bestSeason) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.rating = rating;
        this.image = image;
        this.region = region;
        this.bestSeason = bestSeason;
        this.hotelCapacityLimit = 2; // Default
    }

    // New constructor with maxCapacity
    public Hotel(String name, String location, Double price, Double rating, String image, String region, String bestSeason, Integer maxCapacity) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.rating = rating;
        this.image = image;
        this.region = region;
        this.bestSeason = bestSeason;
        this.hotelCapacityLimit = maxCapacity != null ? maxCapacity : 2;
    }

    // legacy/ghost overloads
    public Hotel(String name, String location, Double price, Double rating, String image, String region) {
        this(name, location, price, rating, image, region, "Year-round", 2);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getBestSeason() { return bestSeason; }
    public void setBestSeason(String bestSeason) { this.bestSeason = bestSeason; }

    public int getHotelCapacityLimit() { return hotelCapacityLimit; }
    public void setHotelCapacityLimit(int hotelCapacityLimit) { this.hotelCapacityLimit = hotelCapacityLimit; }
}
