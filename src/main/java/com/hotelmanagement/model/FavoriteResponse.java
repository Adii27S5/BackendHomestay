package com.hotelmanagement.model;

public class FavoriteResponse {
    private Long id;
    private String userEmail;
    private Long itemId;
    private String type;
    private Object details; // Homestay or Attraction

    public FavoriteResponse() {}

    public FavoriteResponse(Favorite favorite, Object details) {
        this.id = favorite.getId();
        this.userEmail = favorite.getUserEmail();
        this.itemId = favorite.getItemId();
        this.type = favorite.getType();
        this.details = details;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Object getDetails() { return details; }
    public void setDetails(Object details) { this.details = details; }
}
