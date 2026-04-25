package com.hotelmanagement.service;

import com.hotelmanagement.model.Hotel;
import com.hotelmanagement.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Transactional(readOnly = true)
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Hotel> searchHotels(String query) {
        if (query == null || query.trim().isEmpty()) return getAllHotels();
        return hotelRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(query, query);
    }

    @Transactional(readOnly = true)
    public List<Hotel> getHotelsByRegion(String region) {
        return hotelRepository.findByRegion(region);
    }

    @Transactional(readOnly = true)
    public List<Hotel> getHotelsByLocation(String location) {
        if (location == null || location.trim().isEmpty()) return getAllHotels();
        return hotelRepository.findByHotelLocation(location);
    }

    @Transactional(readOnly = true)
    public Optional<Hotel> getHotelById(@NonNull Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(@NonNull Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(@NonNull Long id, @NonNull Hotel hotelDetails) {
        Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        
        hotel.setName(hotelDetails.getName());
        hotel.setLocation(hotelDetails.getLocation());
        hotel.setPrice(hotelDetails.getPrice());
        hotel.setRating(hotelDetails.getRating());
        hotel.setImage(hotelDetails.getImage());
        hotel.setRegion(hotelDetails.getRegion());
        hotel.setBestSeason(hotelDetails.getBestSeason());
        hotel.setHotelCapacityLimit(hotelDetails.getHotelCapacityLimit());
        
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(@NonNull Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new RuntimeException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }
}
