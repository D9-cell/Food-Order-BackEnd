package com.deepon.service.Food;

import com.deepon.model.Category;
import com.deepon.model.Food;
import com.deepon.model.Restaurant;
import com.deepon.repository.FoodRepository;
import com.deepon.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;



    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
        Food food = new Food();

        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(request.getDescription());
        food.setImages(request.getImages());
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setIngredients(request.getIngredients());
        food.setSeasonal(request.isSeasonal());
        food.setVegetarian(request.isVegetarian());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVegetarian, boolean isSeasonal,String foodCategory) throws Exception {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods = filterByVegetarian(foods);
        }
        if(isNonVegetarian){
            foods = filterByNonVegetarian(foods);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods);
        }
        if(foodCategory!= null && !foodCategory.isEmpty()){
            foods = filterByCategory(foods,foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }
    private List<Food> filterByVegetarian(List<Food> foods) {
        return foods.stream().filter(food -> food.isVegetarian() == true).collect(Collectors.toList());
    }
    private List<Food> filterByNonVegetarian(List<Food> foods) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }
    private List<Food> filterBySeasonal(List<Food> foods) {
        return foods.stream().filter(food -> food.isSeasonal() == true).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long id) throws Exception {
        Optional<Food> food = foodRepository.findById(id);
        if(food.isEmpty()){
            throw new Exception("Food Not Exists.....");
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
