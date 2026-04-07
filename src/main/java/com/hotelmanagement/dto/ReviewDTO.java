package com.hotelmanagement.dto;

import org.springframework.lang.NonNull;
import java.time.LocalDateTime;

public class ReviewDTO {

    private Long id;
    private Long attractionId;
    private String userEmail;
    private String userName;
    private Double rating;
    private String comment;
    private LocalDateTime date;

    public ReviewDTO() {}

    public ReviewDTO(@NonNull Long id, Long attractionId, String userEmail, String userName, Double rating, String comment, LocalDateTime date) {
        this.id = id;
        this.attractionId = attractionId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAttractionId() { return attractionId; }
    public void setAttractionId(Long attractionId) { this.attractionId = attractionId; }

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
