package com.hotelmanagement.controller;

import com.hotelmanagement.model.Food;
import com.hotelmanagement.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public List<Food> getAllFoods(@RequestParam(required = false) String location, @RequestParam(required = false) String region) {
        if (region != null && !region.isEmpty()) {
            return foodService.getFoodsByRegion(region);
        }
        if (location != null && !location.isEmpty()) {
            return foodService.getFoodsByLocation(location);
        }
        return foodService.getAllFoods();
    }

    @GetMapping("/location/{location}")
    public List<Food> getFoodsByLocation(@PathVariable String location) {
        return foodService.getFoodsByLocationMatch(location);
    }

    @GetMapping("/region/{region}")
    public List<Food> getFoodsByRegion(@PathVariable String region) {
        return foodService.getFoodsByRegion(region);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@NonNull @PathVariable Long id) {
        return foodService.getFoodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Food createFood(@NonNull @RequestBody Food food) {
        return foodService.saveFood(food);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@NonNull @PathVariable Long id, @NonNull @RequestBody Food details) {
        try {
            return ResponseEntity.ok(foodService.modifyFoodDetails(id, details));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@NonNull @PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.ok().build();
    }
}
