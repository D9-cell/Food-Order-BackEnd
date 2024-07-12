package com.deepon.service.Category;

import com.deepon.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(String name,Long userId) throws Exception;

    List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

    Category findCategoryById(Long id) throws Exception;
}
