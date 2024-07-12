package com.deepon.service.Food;

import com.deepon.model.Category;
import com.deepon.model.Food;
import com.deepon.model.Restaurant;
import com.deepon.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);
    void deleteFood(Long foodId) throws Exception;

    List<Food> getRestaurantsFood(Long restaurantId,boolean isVegetarian,boolean isNonVegetarian,boolean isSeasonal,String foodCategory) throws Exception;

    List<Food> searchFood(String keyword);
    Food findFoodById(Long id) throws Exception;
    Food updateAvailabilityStatus(Long foodId) throws Exception;
}
