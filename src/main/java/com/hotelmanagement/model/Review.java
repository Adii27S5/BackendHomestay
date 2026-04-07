// RE-INDEX: 2026-03-26T21:31:00
package com.hotelmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Force Refresh: 2026-03-26T21:27:00
 */
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id", nullable = false)
    private Attraction attraction;

    private String userEmail;
    private String userName;
    private Double rating;
    
    @Column(length = 2000)
    private String comment;
    
    private LocalDateTime date;

    public Review() {
        this.date = LocalDateTime.now();
    }

    public Review(Attraction attraction, String userEmail, String userName, Double rating, String comment) {
        this.attraction = attraction;
        this.userEmail = userEmail;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Attraction getAttraction() { return attraction; }
    public void setAttraction(Attraction attraction) { this.attraction = attraction; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
