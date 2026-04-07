package com.hotelmanagement.repository;

import com.hotelmanagement.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.repository.query.Param;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRegion(String region);
    
    @Query("SELECT f FROM Food f WHERE LOWER(:location) LIKE LOWER(CONCAT('%', f.location, '%'))")
    List<Food> findByLocationMatch(@Param("location") String location);
    
    @Query("SELECT f FROM Food f WHERE f.location LIKE %:location%")
    List<Food> findByLocation(@Param("location") String location);
}
