package com.hotelmanagement.repository;

import com.hotelmanagement.model.Homestay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, Long> {
    List<Homestay> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(String title, String location);
    List<Homestay> findByRegion(String region);
    java.util.Optional<Homestay> findByTitle(String title);
    @Query("SELECT s FROM Homestay s WHERE s.location LIKE %:location%")
    List<Homestay> findByLocation(@Param("location") String location);
}
