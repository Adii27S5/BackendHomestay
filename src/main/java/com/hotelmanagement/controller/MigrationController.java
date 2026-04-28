package com.hotelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class MigrationController {


    @GetMapping
    public ResponseEntity<java.util.Map<String, String>> getAdminLinks() {
        java.util.Map<String, String> links = new java.util.LinkedHashMap<>();
        links.put("all_users", "/api/admin/users");
        links.put("all_homestays", "/api/admin/homestays");
        links.put("all_tours", "/api/admin/tours");
        links.put("all_hotels", "/api/admin/hotels");
        return ResponseEntity.ok(links);
    }

    @Autowired
    private com.hotelmanagement.repository.AppUserRepository userRepository;

    @Autowired
    private com.hotelmanagement.repository.HomestayRepository homestayRepository;

    @Autowired
    private com.hotelmanagement.repository.AttractionRepository attractionRepository;

    @Autowired
    private com.hotelmanagement.repository.HotelRepository hotelRepository;

    @GetMapping("/users")
    public ResponseEntity<java.util.List<com.hotelmanagement.model.AppUser>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/homestays")
    public ResponseEntity<java.util.List<com.hotelmanagement.model.Homestay>> getAllHomestays() {
        return ResponseEntity.ok(homestayRepository.findAll());
    }

    @GetMapping("/tours")
    public ResponseEntity<java.util.List<com.hotelmanagement.model.Attraction>> getAllTours() {
        return ResponseEntity.ok(attractionRepository.findAll());
    }

    @GetMapping("/hotels")
    public ResponseEntity<java.util.List<com.hotelmanagement.model.Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelRepository.findAll());
    }

    @GetMapping("/sync-bookings")
    public ResponseEntity<String> syncBookings() {
        return ResponseEntity.ok("Migration service currently disabled.");
    }
}
