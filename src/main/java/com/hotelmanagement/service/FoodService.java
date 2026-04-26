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

    public Food updateFoodDetails(@NonNull Long id, @NonNull Food details) {
        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
        
        if (details.getTitle() != null) food.setTitle(details.getTitle());
        if (details.getLocation() != null) food.setLocation(details.getLocation());
        if (details.getDescription() != null) food.setDescription(details.getDescription());
        if (details.getPrice() != null) food.setPrice(details.getPrice());
        if (details.getImage() != null) food.setImage(details.getImage());
        if (details.getRating() != null) food.setRating(details.getRating());
        if (details.getCategory() != null) food.setCategory(details.getCategory());
        if (details.getRegion() != null) food.setRegion(details.getRegion());
        
        return foodRepository.save(food);
    }

    public void deleteFood(@NonNull Long id) {
        foodRepository.deleteById(id);
    }
}
