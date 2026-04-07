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
        
        homestay.setImage(details.getImage());
        homestay.setTitle(details.getTitle());
        homestay.setLocation(details.getLocation());
        homestay.setRating(details.getRating());
        homestay.setPrice(details.getPrice());
        homestay.setHost(details.getHost());
        homestay.setGuests(details.getGuests());
        homestay.setAmenities(details.getAmenities());
        homestay.setCategory(details.getCategory());
        homestay.setMaxCapacity(details.getMaxCapacity());
        
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
