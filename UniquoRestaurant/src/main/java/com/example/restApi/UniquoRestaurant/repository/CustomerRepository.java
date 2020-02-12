package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restApi.UniquoRestaurant.entity.Customer;
import com.example.restApi.UniquoRestaurant.entity.Person;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}