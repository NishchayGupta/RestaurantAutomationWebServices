package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restApi.UniquoRestaurant.entity.OrderFood;

public interface OrderFoodRepository extends JpaRepository<OrderFood, Integer>{

	@Query(value = "select * from order_food where customer_id = ?1 AND existing_order = 1", nativeQuery = true)
	OrderFood findByCustomerId(int customerId);
}