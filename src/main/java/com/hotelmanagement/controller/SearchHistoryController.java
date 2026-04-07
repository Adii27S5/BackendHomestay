package com.hotelmanagement.controller;

import com.hotelmanagement.model.SearchHistory;
import com.hotelmanagement.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search-history")
@CrossOrigin(origins = "*")
public class SearchHistoryController {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @GetMapping
    public List<SearchHistory> getHistory(@RequestParam String email) {
        return searchHistoryRepository.findByUserEmailOrderByTimestampDesc(email);
    }

    @PostMapping
    public SearchHistory addHistory(@RequestBody Map<String, String> payload) {
        String email = payload.get("userEmail");
        String query = payload.get("query");
        if (email == null || query == null) return null;
        
        SearchHistory history = new SearchHistory(email, query);
        return searchHistoryRepository.save(history);
    }

    @DeleteMapping
    public ResponseEntity<?> clearHistory(@RequestParam String email) {
        searchHistoryRepository.deleteByUserEmail(email);
        return ResponseEntity.ok().build();
    }
}
