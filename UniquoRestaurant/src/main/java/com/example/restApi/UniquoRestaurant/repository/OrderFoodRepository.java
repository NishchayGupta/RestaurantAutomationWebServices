package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restApi.UniquoRestaurant.entity.OrderFood;

public interface OrderFoodRepository extends JpaRepository<OrderFood, Integer>{

}