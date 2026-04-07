package com.hotelmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entity;

    @Column(nullable = false)
    private String user;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String amount;

    @Column
    private String image;

    @Column
    private String location;

    @Column
    private String title;

    @Column
    private String host;

    @Column
    private Long homestayId;

    @Column
    private String startISO;

    @Column
    private Integer guestsCount;

    @Column(length = 2000)
    private String selectedTours;

    @Column(length = 2000)
    private String selectedFoods;

    public Booking() {}

    public Booking(Long id, String entity, String user, String userEmail, String date, String status, String amount, String image, String location, String title, String host, Long homestayId, String startISO, Integer guestsCount, String selectedTours, String selectedFoods) {
        this.id = id;
        this.entity = entity;
        this.user = user;
        this.userEmail = userEmail;
        this.date = date;
        this.status = status;
        this.amount = amount;
        this.image = image;
        this.location = location;
        this.title = title;
        this.host = host;
        this.homestayId = homestayId;
        this.startISO = startISO;
        this.guestsCount = guestsCount;
        this.selectedTours = selectedTours;
        this.selectedFoods = selectedFoods;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public Long getHomestayId() { return homestayId; }
    public void setHomestayId(Long homestayId) { this.homestayId = homestayId; }

    public String getStartISO() { return startISO; }
    public void setStartISO(String startISO) { this.startISO = startISO; }
 
    public Integer getGuestsCount() { return guestsCount; }
    public void setGuestsCount(Integer guestsCount) { this.guestsCount = guestsCount; }

    public String getSelectedTours() { return selectedTours; }
    public void setSelectedTours(String selectedTours) { this.selectedTours = selectedTours; }

    public String getSelectedFoods() { return selectedFoods; }
    public void setSelectedFoods(String selectedFoods) { this.selectedFoods = selectedFoods; }
}
