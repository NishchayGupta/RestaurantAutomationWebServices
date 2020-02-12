package com.example.restApi.UniquoRestaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.restApi.UniquoRestaurant.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{
	
	@Query(value = "SELECT p from Person p where p.email=?1 and p.password=?2")
	Person findPersonByEmailAndPassword(String email, String password);
}