package com.hotelmanagement.controller;

import com.hotelmanagement.model.AIData;
import com.hotelmanagement.service.AIDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIDataController {
    @Autowired
    private AIDataService aiDataService;

    @PostMapping("/fetch")
    public AIData fetchMockAIData(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        if (query == null || query.trim().isEmpty()) {
            query = "Default system insight request";
        }
        return aiDataService.fetchAndStoreData(query);
    }

    @GetMapping
    public List<AIData> getAllAIData() {
        return aiDataService.getAllData();
    }
}
