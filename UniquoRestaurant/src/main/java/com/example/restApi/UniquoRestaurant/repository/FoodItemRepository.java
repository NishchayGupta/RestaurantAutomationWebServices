package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restApi.UniquoRestaurant.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer>{

}