package com.hotelmanagement.service;

import com.hotelmanagement.model.Favorite;
import com.hotelmanagement.model.FavoriteResponse;
import com.hotelmanagement.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private HomestayService homestayService;

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private HotelService hotelService;

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getAllFavoritesPopulated(String email) {
        List<Favorite> favorites;
        if (email != null && !email.isEmpty()) {
            favorites = favoriteRepository.findByUserEmail(email);
        } else {
            favorites = favoriteRepository.findAll();
        }
        List<FavoriteResponse> responses = new ArrayList<>();

        for (Favorite fav : favorites) {
            Object data = null;
            Long itemId = fav.getItemId();
            if (itemId != null) {
                if ("homestay".equalsIgnoreCase(fav.getType())) {
                    data = homestayService.getHomestayById(itemId).orElse(null);
                } else if ("attraction".equalsIgnoreCase(fav.getType())) {
                    data = attractionService.getAttractionById(itemId).orElse(null);
                } else if ("hotel".equalsIgnoreCase(fav.getType())) {
                    data = hotelService.getHotelById(itemId).orElse(null);
                }
            }
            responses.add(new FavoriteResponse(fav, data));
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    @Transactional
    public @NonNull Favorite addFavorite(@NonNull Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteFavorite(@NonNull Long id) {
        favoriteRepository.deleteById(id);
    }

    @Transactional
    public void removeFavorite(String userEmail, String type, Long itemId) {
        favoriteRepository.findByUserEmailAndItemIdAndType(userEmail, itemId, type)
            .ifPresent(favoriteRepository::delete);
    }

    @Transactional
    public boolean toggleFavorite(@NonNull String userEmail, @NonNull String type, @NonNull Long itemId) {
        var existing = favoriteRepository.findByUserEmailAndItemIdAndType(userEmail, itemId, type);
        if (existing.isPresent()) {
            favoriteRepository.deleteByUserEmailAndItemIdAndType(userEmail, itemId, type);
            return false;
        } else {
            favoriteRepository.save(new Favorite(userEmail, itemId, type));
            return true;
        }
    }
}
