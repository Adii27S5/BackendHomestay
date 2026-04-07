package com.hotelmanagement.service;

import com.hotelmanagement.model.AIData;
import com.hotelmanagement.repository.AIDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIDataService {
    @Autowired
    private AIDataRepository aiDataRepository;

    public AIData fetchAndStoreData(String promptQuery) {
        // Mocking an AI Fetch from a hypothetical external endpoint
        String mockedAIResponse = "Insight generated for [" + promptQuery + "]. " +
                                  "Our intelligent engine has analyzed this tourism/booking data effectively.";
        
        AIData aiData = new AIData(promptQuery, mockedAIResponse);
        return aiDataRepository.save(aiData);
    }

    public List<AIData> getAllData() {
        return aiDataRepository.findAll();
    }
}
