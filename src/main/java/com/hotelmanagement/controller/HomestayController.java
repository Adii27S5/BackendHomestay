package com.hotelmanagement.controller;

import com.hotelmanagement.model.Homestay;
import com.hotelmanagement.service.HomestayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homestays")
@CrossOrigin(origins = "*")
public class HomestayController {

    @Autowired
    private HomestayService homestayService;

    @GetMapping
    public List<Homestay> getAllHomestays(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String location) {
        if (location != null && !location.trim().isEmpty()) {
            return homestayService.getHomestaysByLocation(location);
        }
        if (search != null && !search.trim().isEmpty()) {
            return homestayService.searchHomestays(search);
        }
        return homestayService.getAllHomestays();
    }

    @GetMapping("/pending")
    public List<Homestay> getPendingHomestays() {
        return homestayService.getPendingHomestays();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Void> approveHomestay(@NonNull @PathVariable Long id) {
        try {
            homestayService.approveHomestay(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Homestay> getHomestayById(@NonNull @PathVariable Long id) {
        return homestayService.getHomestayById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Homestay createHomestay(@NonNull @RequestBody Homestay homestay) {
        return homestayService.createHomestay(homestay);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Homestay> updateHomestay(@NonNull @PathVariable Long id, @NonNull @RequestBody Homestay details) {
        try {
            return ResponseEntity.ok(homestayService.updateHomestay(id, details));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHomestay(@NonNull @PathVariable Long id) {
        try {
            homestayService.deleteHomestay(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/deny")
    public ResponseEntity<Void> denyHomestay(@NonNull @PathVariable Long id) {
        try {
            homestayService.denyHomestay(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
