package com.example.restApi.UniquoRestaurant.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.restApi.UniquoRestaurant.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query(value = "select c.* from Customer c JOIN Person p where c.person_customer = (SELECT p1.person_id from Person p1 where p1.email=?1)", nativeQuery = true)
	Customer findByEmailAddress(String email);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE customer SET table_customer = 11, order_type = 'Takeout'\n" + 
			"where id=?1", nativeQuery = true)
	int updateById(int id);
}