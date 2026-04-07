package com.hotelmanagement.controller;

import com.hotelmanagement.model.UserAlert;
import com.hotelmanagement.service.UserAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class UserAlertController {
    @Autowired
    private UserAlertService userAlertService;

    @GetMapping("/ping")
    public String ping() {
        return "UserAlert Backend Active";
    }

    @GetMapping("/user/{userEmail}")
    public List<UserAlert> getAlerts(@PathVariable String userEmail) {
        return userAlertService.getAlertsForUser(userEmail);
    }

    @GetMapping("/user/{userEmail}/unread")
    public List<UserAlert> getUnreadAlerts(@PathVariable String userEmail) {
        return userAlertService.getUnreadAlertsForUser(userEmail);
    }

    @PostMapping("/{alertId}/read")
    public void markAsRead(@PathVariable @org.springframework.lang.NonNull Long alertId) {
        userAlertService.markAsRead(alertId);
    }

    @PostMapping("/user/{userEmail}/read-all")
    public void markAllAsRead(@PathVariable String userEmail) {
        userAlertService.markAllAsRead(userEmail);
    }
}
