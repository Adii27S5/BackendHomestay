package com.hotelmanagement.dto;

import com.hotelmanagement.model.Attraction;
import com.hotelmanagement.model.Food;
import java.util.List;

public class AttractionWithFoodsDTO {
    private Attraction attraction;
    private List<Food> foods;
    private String guideEmail;

    public AttractionWithFoodsDTO() {}

    public AttractionWithFoodsDTO(Attraction attraction, List<Food> foods, String guideEmail) {
        this.attraction = attraction;
        this.foods = foods;
        this.guideEmail = guideEmail;
    }

    public Attraction getAttraction() { return attraction; }
    public void setAttraction(Attraction attraction) { this.attraction = attraction; }

    public List<Food> getFoods() { return foods; }
    public void setFoods(List<Food> foods) { this.foods = foods; }

    public String getGuideEmail() { return guideEmail; }
    public void setGuideEmail(String guideEmail) { this.guideEmail = guideEmail; }
}
