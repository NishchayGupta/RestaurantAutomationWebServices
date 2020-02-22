package com.example.restApi.UniquoRestaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restApi.UniquoRestaurant.entity.OrderFood;

public interface OrderFoodRepository extends JpaRepository<OrderFood, Integer>{
}