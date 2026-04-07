package com.hotelmanagement.controller;

import com.hotelmanagement.model.Hotel;
import com.hotelmanagement.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*") // Allows requests from the React frontend
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels(
            @RequestParam(required = false) String search, 
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String location) {
        if (location != null && !location.trim().isEmpty()) {
            return hotelService.getHotelsByLocation(location);
        }
        if (region != null && !region.trim().isEmpty()) {
            return hotelService.getHotelsByRegion(region);
        }
        if (search != null && !search.trim().isEmpty()) {
            return hotelService.searchHotels(search);
        }
        return hotelService.getAllHotels();
    }

    @GetMapping("/region/{region}")
    public List<Hotel> getHotelsByRegion(@PathVariable String region) {
        return hotelService.getHotelsByRegion(region);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@NonNull @PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hotel createHotel(@NonNull @RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@NonNull @PathVariable Long id, @NonNull @RequestBody Hotel hotelDetails) {
        try {
            Hotel updatedHotel = hotelService.updateHotel(id, hotelDetails);
            return ResponseEntity.ok(updatedHotel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@NonNull @PathVariable Long id) {
        try {
            hotelService.deleteHotel(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
