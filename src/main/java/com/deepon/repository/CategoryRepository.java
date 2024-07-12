package com.deepon.repository;

import com.deepon.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findByRestaurantId(Long id);
}
