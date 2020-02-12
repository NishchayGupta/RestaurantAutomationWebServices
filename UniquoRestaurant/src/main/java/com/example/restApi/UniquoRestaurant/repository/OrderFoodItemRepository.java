package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restApi.UniquoRestaurant.entity.OrderFoodItem;

@Repository
public interface OrderFoodItemRepository extends JpaRepository<OrderFoodItem, Integer>{

}
