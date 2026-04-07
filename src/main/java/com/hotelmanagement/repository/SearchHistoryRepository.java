package com.hotelmanagement.repository;

import com.hotelmanagement.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserEmailOrderByTimestampDesc(String userEmail);
    void deleteByUserEmail(String userEmail);
}
