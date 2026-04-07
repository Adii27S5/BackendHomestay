package com.hotelmanagement.service;

import com.hotelmanagement.dto.ReviewDTO;
import com.hotelmanagement.model.Attraction;
import com.hotelmanagement.model.Review;
import com.hotelmanagement.repository.AttractionRepository;
import com.hotelmanagement.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    private ReviewDTO toDto(@NonNull Review r) {
        Long aid = (r.getAttraction() != null) ? r.getAttraction().getId() : null;
        return new ReviewDTO(
            Objects.requireNonNull(r.getId(), "Review ID cannot be null for DTO conversion"), 
            aid,
            r.getUserEmail(), r.getUserName(),
            r.getRating(), r.getComment(), r.getDate()
        );
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByAttraction(Long attractionId) {
        return reviewRepository.findByAttractionId(attractionId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByUser(String userEmail) {
        return reviewRepository.findByUserEmail(userEmail).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO saveReview(Map<String, Object> payload) {
        if (payload == null) throw new IllegalArgumentException("Payload cannot be null");
        
        Object attIdObj = payload.get("attractionId");
        if (attIdObj == null) throw new IllegalArgumentException("attractionId is required");
        
        Long attractionId = Long.valueOf(attIdObj.toString());
        Attraction attraction = attractionRepository.findById(Objects.requireNonNull(attractionId, "attractionId cannot be null"))
            .orElseThrow(() -> new RuntimeException("Attraction not found: " + attractionId));

        Double rating = (payload.get("rating") != null) 
                        ? Double.valueOf(payload.get("rating").toString()) 
                        : 0.0;

        Review r = new Review(
            attraction,
            (String) payload.get("userEmail"),
            (String) payload.get("userName"),
            rating,
            (String) payload.get("comment")
        );
        return toDto(reviewRepository.save(r));
    }

    @Transactional
    public void deleteReview(@NonNull Long id) {
        reviewRepository.deleteById(id);
    }
}
