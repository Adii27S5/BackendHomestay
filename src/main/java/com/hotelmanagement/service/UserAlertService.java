package com.hotelmanagement.service;

import com.hotelmanagement.model.UserAlert;
import com.hotelmanagement.repository.UserAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAlertService {
    @Autowired
    private UserAlertRepository userAlertRepository;

    public void createAlert(String userEmail, String message, String type) {
        UserAlert alert = new UserAlert(userEmail, message, type);
        userAlertRepository.save(alert);
    }

    public List<UserAlert> getAlertsForUser(String userEmail) {
        return userAlertRepository.findByUserEmailOrderByCreatedAtDesc(userEmail);
    }

    public List<UserAlert> getUnreadAlertsForUser(String userEmail) {
        return userAlertRepository.findByUserEmailAndIsReadOrderByCreatedAtDesc(userEmail, false);
    }

    public void markAsRead(@org.springframework.lang.NonNull Long alertId) {
        userAlertRepository.findById(alertId).ifPresent(n -> {
            n.setRead(true);
            userAlertRepository.save(n);
        });
    }

    public void markAllAsRead(String userEmail) {
        List<UserAlert> unread = userAlertRepository.findByUserEmailAndIsReadOrderByCreatedAtDesc(userEmail, false);
        unread.forEach(n -> n.setRead(true));
        userAlertRepository.saveAll(unread);
    }
}
