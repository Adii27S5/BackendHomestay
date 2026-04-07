package com.hotelmanagement.repository;

import com.hotelmanagement.model.AIData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIDataRepository extends JpaRepository<AIData, Long> {
}
