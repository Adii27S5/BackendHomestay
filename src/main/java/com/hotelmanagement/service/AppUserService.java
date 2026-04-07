package com.hotelmanagement.service;

import com.hotelmanagement.model.AppUser;
import com.hotelmanagement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserAlertService userAlertService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    public void sendOtp(String email, String otp) {
        emailNotificationService.sendOtpEmail(email, otp);
    }

    @Transactional(readOnly = true)
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AppUser> getUserById(@NonNull Long id) {
        return appUserRepository.findById(id);
    }

    public AppUser saveUser(@NonNull AppUser user) {
        // Prevent duplicate emails during signup
        if (user.getId() == null) {
            Optional<AppUser> existing = appUserRepository.findByEmail(user.getEmail());
            if (existing.isPresent()) {
                throw new RuntimeException("User with this email already exists!");
            }
        }
        
        AppUser savedUser = appUserRepository.save(user);
        if (user.getId() == null) {
            userAlertService.createAlert("admin@tournest.com", 
                "New Explorer Joined: " + savedUser.getFullName() + " (" + savedUser.getRole() + ")", "SUCCESS");
        }
        return savedUser;
    }

    @Transactional(readOnly = true)
    public Optional<AppUser> getUserByEmail(String email) {
        if (email == null) return Optional.empty();
        return appUserRepository.findByEmail(email.toLowerCase());
    }

    public void deleteUser(@NonNull Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        appUserRepository.deleteById(id);
    }

    public void processForgotPassword(String email) {
        Optional<AppUser> userOpt = appUserRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();
            String otp = String.format("%06d", new java.util.Random().nextInt(999999));
            user.setOtp(otp);
            user.setOtpExpiry(java.time.LocalDateTime.now().plusMinutes(2));
            appUserRepository.save(user);
            
            emailNotificationService.sendOtpEmail(email, otp);
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

    public boolean verifyOtp(String email, String otp) {
        return appUserRepository.findByEmail(email)
            .map(user -> user.getOtp() != null && user.getOtp().equals(otp) &&
                          user.getOtpExpiry() != null && user.getOtpExpiry().isAfter(java.time.LocalDateTime.now()))
            .orElse(false);
    }

    public void resetPassword(String email, String newPassword) {
        Optional<AppUser> userOpt = appUserRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();
            user.setPassword(newPassword);
            user.setOtp(null);
            user.setOtpExpiry(null);
            appUserRepository.save(user);
        } else {
            throw new RuntimeException("User not found: " + email);
        }
    }

    @NonNull
    public AppUser updateUserPreferences(String email, @NonNull AppUser details) {
        AppUser user = appUserRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        
        if (details.getLanguage() != null) user.setLanguage(details.getLanguage());
        if (details.getCurrency() != null) user.setCurrency(details.getCurrency());
        if (details.getTheme() != null) user.setTheme(details.getTheme());
        
        return appUserRepository.save(user);
    }
}

