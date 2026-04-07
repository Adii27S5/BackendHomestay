package com.hotelmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private Long itemId; // ID of Homestay or Attraction

    @Column(nullable = false)
    private String type; // HOMESTAY, ATTRACTION, HOTEL

    public Favorite() {}

    public Favorite(String userEmail, Long itemId, String type) {
        this.userEmail = userEmail;
        this.itemId = itemId;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
