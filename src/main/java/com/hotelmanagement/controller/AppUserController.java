package com.hotelmanagement.controller;

import com.hotelmanagement.model.AppUser;
import com.hotelmanagement.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class AppUserController {

    @Autowired
    private AppUserService userService;

    @GetMapping
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<AppUser> getUser(@PathVariable String identifier) {
        // Try parsing as ID first
        try {
            Long id = java.util.Objects.requireNonNull(Long.valueOf(identifier));
            return userService.getUserById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            // If not a number, treat as email
            return userService.getUserByEmail(identifier)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AppUser> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AppUser createUser(@NonNull @RequestBody AppUser user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@NonNull @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{email}")
    public ResponseEntity<AppUser> updatePreferences(@PathVariable String email, @NonNull @RequestBody AppUser details) {
        try {
            return ResponseEntity.ok(userService.updateUserPreferences(email, details));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody java.util.Map<String, String> request) {
        try {
            String email = request.get("email");
            if (email == null || email.isEmpty()) return ResponseEntity.badRequest().body("Email required");
            userService.processForgotPassword(email);
            return ResponseEntity.ok(java.util.Map.of("message", "OTP sent to email"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(java.util.Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody java.util.Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        boolean isValid = userService.verifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok(java.util.Map.of("message", "OTP Verified"));
        }
        return ResponseEntity.status(400).body(java.util.Map.of("error", "Invalid or Expired OTP"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody java.util.Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");
        
        if (userService.verifyOtp(email, otp)) {
            userService.resetPassword(email, newPassword);
            return ResponseEntity.ok(java.util.Map.of("message", "Password Reset Successfully"));
        }
        return ResponseEntity.status(400).body(java.util.Map.of("error", "Invalid or Expired OTP"));
    }
}

