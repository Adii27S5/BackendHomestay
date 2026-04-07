package com.hotelmanagement.controller;

import com.hotelmanagement.dto.ReviewDTO;
import com.hotelmanagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private com.hotelmanagement.service.EmailNotificationService emailNotificationService;

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/attraction/{attractionId}")
    public List<ReviewDTO> getReviewsByAttraction(@PathVariable Long attractionId) {
        return reviewService.getReviewsByAttraction(attractionId);
    }

    @GetMapping("/user/{userEmail}")
    public List<ReviewDTO> getReviewsByUser(@PathVariable String userEmail) {
        return reviewService.getReviewsByUser(userEmail);
    }

    @PostMapping
    public ReviewDTO saveReview(@RequestBody Map<String, Object> payload) {
        ReviewDTO saved = reviewService.saveReview(payload);
        // Send confirmation email from controller — keeps ReviewService free of email concerns
        try {
            if (emailNotificationService != null && saved != null) {
                String targetEmail = (saved.getUserEmail() != null) ? saved.getUserEmail() : "";
                String name  = (saved.getUserName()  != null)    ? saved.getUserName()     : "Traveler";
                Object attNameObj = payload.get("attractionName");
                String place = (attNameObj != null) ? attNameObj.toString() : "your recent visit";
                
                if (!targetEmail.isEmpty()) {
                    emailNotificationService.notifyReviewerOfPublishedReview(targetEmail, name, place);
                }
            }
        } catch (Exception e) {
            // Never fail the API response over an email error
            System.err.println("[ReviewController] Email notification failed: " + e.getMessage());
        }
        return saved;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@NonNull @PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
