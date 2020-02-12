package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restApi.UniquoRestaurant.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{

}