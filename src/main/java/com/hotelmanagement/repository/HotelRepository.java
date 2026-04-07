package com.hotelmanagement.repository;

import com.hotelmanagement.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);
    List<Hotel> findByRegion(String region);
    
    @org.springframework.data.jpa.repository.Query("SELECT h FROM Hotel h WHERE LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Hotel> findByHotelLocation(@org.springframework.data.repository.query.Param("location") String location);
    
    java.util.Optional<Hotel> findByName(String name);
}
