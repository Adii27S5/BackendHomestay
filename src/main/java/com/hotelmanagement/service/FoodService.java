package com.hotelmanagement.service;

import com.hotelmanagement.model.Food;
import com.hotelmanagement.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> getFoodsByLocation(String location) {
        return foodRepository.findByLocation(location);
    }

    public List<Food> getFoodsByRegion(String region) {
        return foodRepository.findByRegion(region);
    }

    public List<Food> getFoodsByLocationMatch(String location) {
        return foodRepository.findByLocationMatch(location);
    }

    public Optional<Food> getFoodById(@NonNull Long id) {
        return foodRepository.findById(id);
    }

    public Food saveFood(@NonNull Food food) {
        return foodRepository.save(food);
    }

    public void deleteFood(@NonNull Long id) {
        foodRepository.deleteById(id);
    }
}
