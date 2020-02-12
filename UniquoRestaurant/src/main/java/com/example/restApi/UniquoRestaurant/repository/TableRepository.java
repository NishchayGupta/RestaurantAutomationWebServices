package com.example.restApi.UniquoRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restApi.UniquoRestaurant.entity.TableRestaurant;

public interface TableRepository extends JpaRepository<TableRestaurant, Integer>{

}