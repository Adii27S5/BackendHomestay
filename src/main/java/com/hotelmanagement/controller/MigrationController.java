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


    @Autowired
    private com.hotelmanagement.repository.AppUserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<java.util.List<com.hotelmanagement.model.AppUser>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/sync-bookings")
    public ResponseEntity<String> syncBookings() {
        return ResponseEntity.ok("Migration service currently disabled.");
    }
}
