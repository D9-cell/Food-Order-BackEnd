package com.deepon.request;

import com.deepon.model.Category;
import com.deepon.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean isVegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;
}
