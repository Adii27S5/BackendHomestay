// RE-INDEX: 2026-03-26T21:31:10
package com.hotelmanagement.repository;

import com.hotelmanagement.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Force Refresh: 2026-03-26T21:27:10
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAttractionId(Long attractionId);
    List<Review> findByUserEmail(String userEmail);
    Long countByAttractionId(Long attractionId);
}
