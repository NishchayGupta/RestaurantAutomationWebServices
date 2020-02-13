package com.example.restApi.UniquoRestaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restApi.UniquoRestaurant.entity.TableRestaurant;

public interface TableRepository extends JpaRepository<TableRestaurant, Integer>{

	@Query(value = "SELECT table_id, booking_date_time, end_date_time, start_date_time, "
			+ "ABS(TIMESTAMPDIFF(MINUTE, NOW(), end_date_time)) waiting_time "
			+ "from table_restaurant order by waiting_time limit 1", nativeQuery = true)
	TableRestaurant checkTableAvailability();
	
	@Query(value = "SELECT table_id, booking_date_time, end_date_time, start_date_time, "
			+ "ABS(TIMESTAMPDIFF(MINUTE, NOW(), end_date_time)) waiting_time "
			+ "from table_restaurant order by waiting_time", nativeQuery = true)
	List<TableRestaurant> checkTableAvailabilityList();
}