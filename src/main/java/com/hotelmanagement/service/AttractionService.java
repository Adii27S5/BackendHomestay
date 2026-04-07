package com.hotelmanagement.service;

import com.hotelmanagement.model.Attraction;
import com.hotelmanagement.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private com.hotelmanagement.repository.FoodRepository foodRepository;

    @Autowired
    private EmailNotificationService emailService;
    
    @Autowired
    private UserAlertService userAlertService;

    @Transactional
    public Attraction createAttractionWithFoods(@NonNull Attraction attraction, java.util.List<com.hotelmanagement.model.Food> foods, String guideEmail) {
        if (guideEmail != null) {
            attraction.setGuideEmail(guideEmail);
        }
        Attraction savedAttraction = attractionRepository.save(attraction);
        
        // Handle Foods
        if (foods != null && !foods.isEmpty()) {
            final String city = attraction.getLocation().split(",")[0].trim();
            final String region = attraction.getRegion();
            foods.forEach(f -> {
                f.setLocation(city);
                f.setRegion(region);
                foodRepository.save(f);
            });
        }

        // Notify Admin of new submission
        userAlertService.createAlert("admin@tournest.com", "New Tour Submission: " + attraction.getTitle(), "SUCCESS");
        
        if (guideEmail != null && !guideEmail.isEmpty()) {
            userAlertService.createAlert(guideEmail, "Tour Submission Received! Admin will review shortly.", "SUCCESS");
        }

        return savedAttraction;
    }


    @Transactional(readOnly = true)
    public List<Attraction> getAllAttractions() {
        return attractionRepository.findByApprovedTrue();
    }

    @Transactional(readOnly = true)
    public List<Attraction> getPendingAttractions() {
        return attractionRepository.findByApprovedFalse();
    }

    @Transactional
    public Attraction approveAttraction(@NonNull Long id) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() -> new RuntimeException("Attraction not found"));
        attraction.setApproved(true);
        
        if (attraction.getGuideEmail() != null && attraction.getGuideEmail().contains("@")) {
            userAlertService.createAlert(attraction.getGuideEmail(), "Your Experience '" + attraction.getTitle() + "' was approved!", "SUCCESS");
            emailService.sendTourPublishedEmail(attraction.getGuideEmail(), attraction.getTitle(), attraction.getLocation());
        } else {
            emailService.sendTourPublishedEmail("tournestofc@gmail.com", attraction.getTitle(), attraction.getLocation());
        }
        
        return attractionRepository.save(attraction);
    }

    @Transactional(readOnly = true)
    public Optional<Attraction> getAttractionById(@NonNull Long id) {
        return attractionRepository.findById(id);
    }

    @Transactional
    public Attraction createAttraction(@NonNull Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    @Transactional
    public Attraction updateAttraction(@NonNull Long id, @NonNull Attraction details) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() -> new RuntimeException("Attraction not found"));
        
        attraction.setImage(details.getImage());
        attraction.setTitle(details.getTitle());
        attraction.setLocation(details.getLocation());
        attraction.setDuration(details.getDuration());
        attraction.setRating(details.getRating());
        attraction.setCategory(details.getCategory());
        attraction.setDescription(details.getDescription());
        
        return attractionRepository.save(attraction);
    }

    @Transactional
    public void deleteAttraction(@NonNull Long id) {
        attractionRepository.deleteById(id);
    }

    @Transactional
    public void denyAttraction(@NonNull Long id) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() -> new RuntimeException("Attraction not found"));
        if (attraction.getGuideEmail() != null && attraction.getGuideEmail().contains("@")) {
            userAlertService.createAlert(attraction.getGuideEmail(), "Your Experience '" + attraction.getTitle() + "' was denied.", "ERROR");
            emailService.sendTourDeniedEmail(attraction.getGuideEmail(), attraction.getTitle());
        }
        attractionRepository.deleteById(id);
    }
}
