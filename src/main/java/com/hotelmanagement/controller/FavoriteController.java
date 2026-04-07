package com.hotelmanagement.controller;

import com.hotelmanagement.model.Favorite;
import com.hotelmanagement.model.FavoriteResponse;
import com.hotelmanagement.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*") // Allows requests from any origin for ease of integration
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public List<FavoriteResponse> getAllFavorites(@RequestParam(required = false) String email) {
        return favoriteService.getAllFavoritesPopulated(email);
    }

    @PostMapping
    public Favorite addFavorite(@NonNull @RequestBody Favorite favorite) {
        return favoriteService.addFavorite(favorite);
    }

    @PostMapping("/toggle")
    public boolean toggleFavorite(@NonNull @RequestParam String email, @NonNull @RequestParam String type, @NonNull @RequestParam Long itemId) {
        return favoriteService.toggleFavorite(email, type, itemId);
    }

    @GetMapping("/check")
    public boolean checkFavorite(@NonNull @RequestParam String email, @NonNull @RequestParam String type, @NonNull @RequestParam Long itemId) {
        return favoriteService.getAllFavoritesPopulated(email).stream()
                .anyMatch(f -> type.equalsIgnoreCase(f.getType()) && itemId.equals(f.getItemId()));
    }

    @DeleteMapping("/{id}")
    public void deleteFavorite(@NonNull @PathVariable Long id) {
        favoriteService.deleteFavorite(id);
    }
}
