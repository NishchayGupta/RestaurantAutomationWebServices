package com.example.restApi.UniquoRestaurant.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.restApi.UniquoRestaurant.entity.TableRestaurant;

public interface TableRepository extends JpaRepository<TableRestaurant, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "Update table_restaurant\n" + 
			"            Join (SELECT t.id as table_id, t.booking_date_time as booking_time, t.end_date_time as end_time, t.start_date_time as start_time, \n" + 
			"            ABS(TIMESTAMPDIFF(MINUTE, NOW(), end_date_time)) result \n" + 
			"            from table_restaurant t\n" + 
			"            where t.id != 11 AND t.id != 12 AND t.id NOT IN (Select table_customer from customer)\n" + 
			"            order by waiting_time) AS t1\n" + 
			"            SET\n" + 
			"            waiting_time = t1.result\n" + 
			"            WHERE id = t1.table_id", nativeQuery = true)
	int updateWaitingTime();
	
	@Query(value = "SELECT id, booking_date_time, end_date_time, start_date_time, \n" + 
			"            waiting_time, order_food \n" + 
			"            from table_restaurant \n" + 
			"            where id != 11 AND id != 12 AND id NOT IN (Select table_customer from customer)\n" + 
			"            order by waiting_time limit 1;", nativeQuery = true)
	TableRestaurant checkTableAvailability();
	
	@Query(value = "SELECT id, booking_date_time, end_date_time, start_date_time, order_food,"
			+ "ABS(TIMESTAMPDIFF(MINUTE, NOW(), end_date_time)) waiting_time "
			+ "from table_restaurant where id != 11 AND id != 12 AND id NOT IN (Select table_customer from customer)"
			+ "order by waiting_time", nativeQuery = true)
	List<TableRestaurant> checkTableAvailabilityList();
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE table_restaurant SET start_date_time = now(), end_date_time = now() WHERE id=?1 ", nativeQuery = true)
	void setCurrentDateTime(int tableId);
}