package com.hotelmanagement.service;

import com.hotelmanagement.model.*;
import com.hotelmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserAlertService userAlertService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookingsByUserEmail(String email) {
        if (email == null) return List.of();
        return bookingRepository.findByUserEmail(email.toLowerCase());
    }

    @Transactional(readOnly = true)
    public Optional<Booking> getBookingById(@NonNull Long id) {
        return bookingRepository.findById(id);
    }

    @NonNull
    public Booking saveBooking(@NonNull Booking booking) {
        if (booking.getGuestsCount() == null) {
            booking.setGuestsCount(1);
        }

        resolveBookingMetatada(booking);
        ensureUserExists(booking);

        boolean isNew = booking.getId() == null;
        Booking saved = bookingRepository.save(booking);
        
        if (isNew) {
            try {
                userAlertService.createAlert("admin@tournest.com", 
                    "New Booking: " + saved.getTitle() + " by " + saved.getUser(), "INFO");
            } catch (Exception e) {
                // Don't fail booking over alert
            }
        }
        
        return saved;
    }

    private void resolveBookingMetatada(Booking booking) {
        Homestay resolvedHomestay = null;

        Long homestayId = booking.getHomestayId();
        if (homestayId != null) {
            resolvedHomestay = homestayRepository.findById(homestayId).orElse(null);
        }

        if (resolvedHomestay == null && booking.getEntity() != null) {
            resolvedHomestay = homestayRepository.findByTitle(booking.getEntity()).orElse(null);
        }

        if (resolvedHomestay != null) {
            updateHomestayOccupancy(resolvedHomestay, booking);
            enrichBookingWithHomestay(booking, resolvedHomestay);
        } else if (booking.getEntity() != null) {
            resolveOtherEntityMetadata(booking);
        }

        if (booking.getStartISO() == null && booking.getDate() != null) {
            String date = booking.getDate();
            if (date.contains(" ")) {
                booking.setStartISO(date.split(" ")[0] + "T14:00:00Z");
            }
        }
    }

    private void updateHomestayOccupancy(Homestay homestay, Booking booking) {
        int currentOccupancy = Optional.ofNullable(homestay.getGuests()).orElse(0);
        int maxCap = Optional.ofNullable(homestay.getMaxCapacity()).orElse(8);
        int bookingGuests = Optional.ofNullable(booking.getGuestsCount()).orElse(1);

        if (currentOccupancy + bookingGuests > maxCap) {
            homestay.setGuests(bookingGuests);
        } else {
            homestay.setGuests(currentOccupancy + bookingGuests);
        }
        homestayRepository.save(homestay);
    }

    private void enrichBookingWithHomestay(Booking booking, Homestay homestay) {
        if (booking.getImage() == null) booking.setImage(homestay.getImage());
        if (booking.getLocation() == null) booking.setLocation(homestay.getLocation());
        if (booking.getTitle() == null) booking.setTitle(homestay.getTitle());
        if (booking.getHost() == null) booking.setHost(homestay.getHost());
        booking.setHomestayId(homestay.getId());
    }

    private void resolveOtherEntityMetadata(Booking booking) {
        Optional<Attraction> attraction = attractionRepository.findByTitle(booking.getEntity());
        if (attraction.isPresent()) {
            Attraction a = attraction.get();
            booking.setImage(a.getImage());
            booking.setLocation(a.getLocation());
            booking.setTitle(a.getTitle());
            booking.setHost("Local Expert");
        } else {
            Optional<Hotel> hotel = hotelRepository.findByName(booking.getEntity());
            if (hotel.isPresent()) {
                Hotel h = hotel.get();
                booking.setLocation(h.getLocation());
                booking.setTitle(h.getName());
                booking.setHost("Hotel Management");
                if (booking.getImage() == null) booking.setImage(h.getImage());
            }
        }
    }

    private void ensureUserExists(Booking booking) {
        if (booking.getUserEmail() != null) {
            String email = booking.getUserEmail().toLowerCase();
            Optional<AppUser> user = appUserRepository.findByEmail(email);
            if (user.isPresent()) {
                AppUser u = user.get();
                if (u.getFullName() == null || u.getFullName().isEmpty()) {
                    u.setFullName(booking.getUser());
                }
                u.setBookingsCount(u.getBookingsCount() != null ? u.getBookingsCount() + 1 : 1);
                appUserRepository.save(u);
            } else {
                try {
                    AppUser newUser = new AppUser();
                    newUser.setEmail(email);
                    newUser.setName(booking.getUser() != null ? booking.getUser() : "Guest");
                    newUser.setFullName(booking.getUser() != null ? booking.getUser() : "Guest User");
                    newUser.setRole("USER");
                    newUser.setPassword("Password123");
                    newUser.setJoined(new java.util.Date().toString());
                    newUser.setStatus("ACTIVE");
                    newUser.setBookingsCount(1);
                    appUserRepository.save(newUser);
                } catch (Exception e) {
                    // Fail silently for user creation
                }
            }
        }
    }

    @NonNull
    public Booking updateBooking(@NonNull Long id, @NonNull Booking details) {
        Booking updated = bookingRepository.findById(id).map(booking -> {
            String oldStatus = booking.getStatus();
            booking.setEntity(details.getEntity());
            booking.setUser(details.getUser());
            booking.setUserEmail(details.getUserEmail());
            booking.setDate(details.getDate());
            booking.setStatus(details.getStatus());
            booking.setAmount(details.getAmount());
            booking.setStartISO(details.getStartISO());
            
            Booking saved = bookingRepository.save(booking);
            
            if (details.getStatus() != null && !details.getStatus().equalsIgnoreCase(oldStatus)) {
                userAlertService.createAlert(saved.getUserEmail(), 
                    "Your booking for " + saved.getTitle() + " is " + saved.getStatus(), "SUCCESS");
                
                if (details.getStatus().equalsIgnoreCase("Approved")) {
                    sendApprovalEmail(saved);
                }
            }
            return saved;
        }).orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return Objects.requireNonNull(updated);
    }

    private void sendApprovalEmail(Booking booking) {
        String guests = String.valueOf(booking.getGuestsCount() != null ? booking.getGuestsCount() : 1);
        if (booking.getSelectedTours() != null && !booking.getSelectedTours().isEmpty()) {
            guests += " (Includes Tours: " + booking.getSelectedTours() + ")";
        }
        if (booking.getSelectedFoods() != null && !booking.getSelectedFoods().isEmpty()) {
            guests += " (Includes Food: " + booking.getSelectedFoods() + ")";
        }
        
        emailNotificationService.sendBookingApprovalEmail(
            booking.getUserEmail(), 
            booking.getUser(), 
            booking.getTitle(), 
            booking.getDate(), 
            guests, 
            booking.getAmount()
        );
    }

    public void deleteBooking(@NonNull Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
