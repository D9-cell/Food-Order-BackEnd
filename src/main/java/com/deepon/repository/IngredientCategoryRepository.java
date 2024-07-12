package com.deepon.repository;

import com.deepon.model.IngredientCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientCategoryRepository extends CrudRepository<IngredientCategory, Long> {

    List<IngredientCategory> findByRestaurantId(Long restaurantId);
}
