package com.hotelmanagement.repository;

import com.hotelmanagement.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    java.util.Optional<Attraction> findByTitle(String title);
    java.util.List<Attraction> findByApprovedTrue();
    java.util.List<Attraction> findByApprovedFalse();
}
