package com.hotelmanagement.repository;

import com.hotelmanagement.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserEmail(String userEmail);
    Optional<Favorite> findByUserEmailAndItemIdAndType(String userEmail, Long itemId, String type);
    void deleteByUserEmailAndItemIdAndType(String userEmail, Long itemId, String type);
}
