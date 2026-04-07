package com.hotelmanagement.repository;

import com.hotelmanagement.model.UserAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserAlertRepository extends JpaRepository<UserAlert, Long> {
    List<UserAlert> findByUserEmailOrderByCreatedAtDesc(String userEmail);
    List<UserAlert> findByUserEmailAndIsReadOrderByCreatedAtDesc(String userEmail, boolean isRead);
}
