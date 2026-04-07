package com.hotelmanagement.controller;

import com.hotelmanagement.model.Attraction;
import com.hotelmanagement.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller managing tourist attractions and regional discovery.
 */
@RestController
@RequestMapping("/api/attractions")
@CrossOrigin(origins = "*")
public class AttractionController {

    @Autowired
    private AttractionService attractionService;

    @GetMapping
    public List<Attraction> getAllAttractions() {
        return attractionService.getAllAttractions();
    }

    @GetMapping("/pending")
    public List<Attraction> getPendingAttractions() {
        return attractionService.getPendingAttractions();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Attraction> approveAttraction(@NonNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(attractionService.approveAttraction(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attraction> getAttractionById(@NonNull @PathVariable Long id) {
        return attractionService.getAttractionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Attraction createAttraction(@NonNull @RequestBody Attraction attraction) {
        return attractionService.createAttraction(attraction);
    }

    @PostMapping("/with-foods")
    public ResponseEntity<Attraction> createAttractionWithFoods(@NonNull @RequestBody com.hotelmanagement.dto.AttractionWithFoodsDTO request) {
        Attraction attraction = request.getAttraction();
        if (attraction == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(attractionService.createAttractionWithFoods(attraction, request.getFoods(), request.getGuideEmail()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attraction> updateAttraction(@NonNull @PathVariable Long id, @NonNull @RequestBody Attraction details) {
        try {
            return ResponseEntity.ok(attractionService.updateAttraction(id, details));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttraction(@NonNull @PathVariable Long id) {
        try {
            attractionService.deleteAttraction(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/deny")
    public ResponseEntity<Void> denyAttraction(@NonNull @PathVariable Long id) {
        try {
            attractionService.denyAttraction(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
