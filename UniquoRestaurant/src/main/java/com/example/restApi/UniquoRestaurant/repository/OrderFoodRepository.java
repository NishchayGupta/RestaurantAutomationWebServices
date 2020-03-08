package com.example.restApi.UniquoRestaurant.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.restApi.UniquoRestaurant.entity.OrderFood;

public interface OrderFoodRepository extends JpaRepository<OrderFood, Integer>{

	@Query(value = "select * from order_food where customer_id = ?1 AND existing_order = 1", nativeQuery = true)
	OrderFood findByCustomerId(int customerId);
	
	@Query(value = "select * from order_food where customer_id = ?1", nativeQuery = true)
	List<OrderFood> findByCustomerIdAllOrders(int customerId);
	
	@Transactional
	@Modifying
	@Query(value = "update order_food SET existing_order = 0 where id = ?1", nativeQuery = true)
	void setExistingOrder(int orderId);
	
	@Transactional
	@Modifying
	@Query(value = "update order_food SET existing_order = 0 where id = (SELECT id from order_food where order_food_table=?1)", nativeQuery = true)
	int setExistingOrderByTable(int tableId);
	
	@Query(value = "select * from order_food where order_food_table=?1 and existing_order = 1", nativeQuery = true)
	OrderFood findByOrderFoodTable(int tableId);
	
	@Query(value = "select * from order_food where order_prepared=0 and existing_order = 1", nativeQuery = true)
	List<OrderFood> findAllExistingOrders();
	
	@Query(value = "select * from order_food where existing_order = 1", nativeQuery = true)
	List<OrderFood> findAllOrders();
}