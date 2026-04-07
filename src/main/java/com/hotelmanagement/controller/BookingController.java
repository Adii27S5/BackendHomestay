package com.hotelmanagement.controller;

import com.hotelmanagement.model.Booking;
import com.hotelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings(@RequestParam(required = false) String email) {
        if (email != null && !email.isEmpty()) {
            return bookingService.getBookingsByUserEmail(email);
        }
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@NonNull @PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@NonNull @RequestBody Booking booking) {
        try {
            return ResponseEntity.ok(bookingService.saveBooking(booking));
        } catch (Exception e) {
            System.err.println("Booking creation failed: " + e.getMessage());
            return ResponseEntity.status(500).body(java.util.Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage() != null ? e.getMessage() : "Unknown error"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@NonNull @PathVariable Long id, @NonNull @RequestBody Booking details) {
        try {
            return ResponseEntity.ok(bookingService.updateBooking(id, details));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@NonNull @PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }
}
