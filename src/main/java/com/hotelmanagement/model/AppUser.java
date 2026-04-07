package com.hotelmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String role;

    @Column(nullable = true)
    private String joined;

    @Column(name = "bookings_count", nullable = true)
    private Integer bookingsCount;

    @Column
    private String status;

    @Column
    private String otp;

    @Column
    private java.time.LocalDateTime otpExpiry;

    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'en'")
    private String language;

    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'INR'")
    private String currency;

    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'light'")
    private String theme;

    public AppUser() {}

    public AppUser(Long id, String name, String fullName, String email, String password, String role, String joined, Integer bookingsCount, String status, String language, String currency, String theme) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.joined = joined;
        this.bookingsCount = bookingsCount;
        this.status = status;
        this.language = language;
        this.currency = currency;
        this.theme = theme;
    }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public java.time.LocalDateTime getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(java.time.LocalDateTime otpExpiry) { this.otpExpiry = otpExpiry; }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getJoined() { return joined; }
    public void setJoined(String joined) { this.joined = joined; }

    public Integer getBookingsCount() { return bookingsCount; }
    public void setBookingsCount(Integer bookingsCount) { this.bookingsCount = bookingsCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}
