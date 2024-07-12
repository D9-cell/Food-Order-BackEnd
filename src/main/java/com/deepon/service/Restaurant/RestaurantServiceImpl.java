package com.deepon.service.Restaurant;

import com.deepon.dto.RestaurantDto;
import com.deepon.model.Address;
import com.deepon.model.Restaurant;
import com.deepon.model.User;
import com.deepon.repository.AddressRepository;
import com.deepon.repository.RestaurantRepository;
import com.deepon.repository.UserRepository;
import com.deepon.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        if(restaurant.getName() != null){
            restaurant.setName(updatedRestaurant.getName());
        }
        if(restaurant.getCuisineType() != null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription() != null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurant.getAddress() != null){
            restaurant.setAddress(updatedRestaurant.getAddress());
        }
        if(restaurant.getContactInformation() != null){
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }
        if(restaurant.getOpeningHours() != null){
            restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
        }
        if(restaurant.getImages() != null){
            restaurant.setImages(updatedRestaurant.getImages());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if(restaurant.isEmpty()){
            throw new Exception("Restaurant not found with id: " + restaurantId);
        }
        return restaurant.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long ownerId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerId);
        if(restaurant == null){
            throw new Exception("Restaurant not found with Owner id: " + ownerId);
        }

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        /** Here contain is not working
        if(user.getFavorites().contains(restaurantDto)){
            user.getFavorites().remove(restaurantDto);
        }else{
            user.getFavorites().add(restaurantDto);
        }*/

        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for(RestaurantDto favorite : favorites){
            if(favorite.getId().equals(restaurantId)){
                isFavorited = true;
                break;
            }
        }
        //If the restaurant is already favorite, remove it. Otherwise, add it to favorite.
        if(isFavorited){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }else{
            favorites.add(restaurantDto);
        }
        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {

        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
