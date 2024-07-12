package com.deepon.dto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.util.List;

@Data
@Embeddable
public class RestaurantDto {

    private String title;
    @Column(length = 1000)
    private List<String> images;

    private String description;
    private Long id;
}

/**
* This DTO class is designed to hold data related to a restaurant, including its title, images, description, and identifier.
* It's intended to be used for transferring this data between different layers of an application,
* such as between the service layer and the controller layer in a Spring Boot application.
* */
