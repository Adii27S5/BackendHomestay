package com.hotelmanagement.service;

import com.hotelmanagement.model.Homestay;
import com.hotelmanagement.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service managing homestay listings and regional discovery queries.
 */
@Service
@Transactional
public class HomestayService {

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private UserAlertService userAlertService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Transactional(readOnly = true)
    public List<Homestay> getAllHomestays() {
        return homestayRepository.findAll().stream()
            .filter(h -> Boolean.TRUE.equals(h.getApproved()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Homestay> getHomestaysByLocation(String location) {
        return homestayRepository.findByLocation(location).stream()
            .filter(h -> Boolean.TRUE.equals(h.getApproved()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Homestay> getPendingHomestays() {
        return homestayRepository.findAll().stream()
            .filter(h -> !Boolean.TRUE.equals(h.getApproved()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Homestay> getHomestaysByRegion(String region) {
        return homestayRepository.findByRegion(region).stream()
            .filter(h -> Boolean.TRUE.equals(h.getApproved()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Homestay> searchHomestays(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllHomestays();
        }
        return homestayRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(query, query).stream()
            .filter(h -> Boolean.TRUE.equals(h.getApproved()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Homestay> getHomestayById(@NonNull Long id) {
        return homestayRepository.findById(id);
    }

    public Homestay createHomestay(@NonNull Homestay homestay) {
        homestay.setApproved(false); // New submissions require approval
        Homestay saved = homestayRepository.save(homestay);
        userAlertService.createAlert("admin@tournest.com", 
            "New Property Pending Approval: " + saved.getTitle(), "PENDING");
        return saved;
    }

    public Homestay updateHomestay(@NonNull Long id, @NonNull Homestay details) {
        Homestay homestay = homestayRepository.findById(id).orElseThrow(() -> new RuntimeException("Homestay not found"));
        
        if (details.getImage() != null) homestay.setImage(details.getImage());
        if (details.getTitle() != null) homestay.setTitle(details.getTitle());
        if (details.getLocation() != null) homestay.setLocation(details.getLocation());
        if (details.getRating() != null) homestay.setRating(details.getRating());
        if (details.getPrice() != null) homestay.setPrice(details.getPrice());
        if (details.getHost() != null) homestay.setHost(details.getHost());
        if (details.getGuests() > 0) homestay.setGuests(details.getGuests());
        if (details.getBedrooms() > 0) homestay.setBedrooms(details.getBedrooms());
        if (details.getBathrooms() > 0) homestay.setBathrooms(details.getBathrooms());
        if (details.getAmenities() != null) homestay.setAmenities(details.getAmenities());
        if (details.getCategory() != null) homestay.setCategory(details.getCategory());
        if (details.getMaxCapacity() > 0) homestay.setMaxCapacity(details.getMaxCapacity());
        if (details.getDescription() != null) homestay.setDescription(details.getDescription());
        if (details.getRegion() != null) homestay.setRegion(details.getRegion());
        if (details.getBestSeason() != null) homestay.setBestSeason(details.getBestSeason());
        if (details.getApproved() != null) homestay.setApproved(details.getApproved());
        
        return homestayRepository.save(homestay);
    }

    public void approveHomestay(@NonNull Long id) {
        Homestay homestay = homestayRepository.findById(id).orElseThrow(() -> new RuntimeException("Homestay not found"));
        homestay.setApproved(true);
        homestayRepository.save(homestay);
        
        String userEmail = homestay.getHost(); // host now stores email
        userAlertService.createAlert(userEmail, "Your property '" + homestay.getTitle() + "' has been approved!", "SUCCESS");
        
        if (userEmail != null && userEmail.contains("@")) {
            emailNotificationService.sendHomestayPublishedEmail(userEmail, homestay.getTitle(), homestay.getLocation());
        }
    }

    public void deleteHomestay(@NonNull Long id) {
        homestayRepository.deleteById(id);
    }

    public void denyHomestay(@NonNull Long id) {
        Homestay homestay = homestayRepository.findById(id).orElseThrow(() -> new RuntimeException("Homestay not found"));
        String userEmail = homestay.getHost(); // host stores email
        if (userEmail != null && userEmail.contains("@")) {
            userAlertService.createAlert(userEmail, "Your property '" + homestay.getTitle() + "' was denied.", "ERROR");
            emailNotificationService.sendHomestayDeniedEmail(userEmail, homestay.getTitle());
        }
        homestayRepository.deleteById(id);
    }
}
