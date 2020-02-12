package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restApi.UniquoRestaurant.entity.Chef;

public interface ChefRepository extends JpaRepository<Chef, Integer>{

}