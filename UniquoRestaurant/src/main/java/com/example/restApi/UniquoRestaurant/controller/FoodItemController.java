package com.example.restApi.UniquoRestaurant.controller;

import java.net.URI;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.restApi.UniquoRestaurant.entity.FoodItem;
import com.example.restApi.UniquoRestaurant.exception.EmptyListException;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.FoodItemRepository;

@RestController
@RequestMapping("/uniquo")
public class FoodItemController {
	
	@Autowired
	private FoodItemRepository foodItemRepo;
	
	@GetMapping("/foodItems")
	public List<FoodItem> getAllFoodItems()
	{
		List<FoodItem> foodItemList = foodItemRepo.findAll();
		if(foodItemList.isEmpty())
		{
			throw new EmptyListException("The food item list is empty");
		}
		return foodItemList;
	}
	
	@GetMapping("/foodItem/{foodItemId}")
	public FoodItem getSingleFoodItem(@PathVariable int foodItemId)
	{
		Optional<FoodItem> foodItemFound = foodItemRepo.findById(foodItemId);
		if(!foodItemFound.isPresent())
		{
			throw new UniquoNotFoundException("id: " +foodItemId);
		}
		FoodItem foodItem = foodItemFound.get();
		return foodItem;
	}
	
	@PostMapping("/foodItem")
	public ResponseEntity<Object> createFoodItem(@RequestBody FoodItem foodItem)
	{
		FoodItem foodItemSaved = foodItemRepo.save(foodItem);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/id")
		.buildAndExpand(foodItemSaved.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/foodItem/{foodItemId}")
	public void removeFoodItem(@PathVariable int foodItemId)
	{
		Optional<FoodItem> foodItemById = foodItemRepo.findById(foodItemId);
		if(!foodItemById.isPresent())
		{
			throw new UniquoNotFoundException("id: " +foodItemId);
		}
		foodItemRepo.deleteById(foodItemId);
	}
	
	@PutMapping("/foodItem/{id}")
	public FoodItem updateFoodItem(@PathVariable int id, @RequestBody FoodItem foodItem)
	{
		Optional<FoodItem> foodItemFetched = foodItemRepo.findById(id);
		FoodItem updateFoodItem = foodItemFetched.get();
		if(foodItemFetched.isPresent())
		{
			updateFoodItem.setFoodItemName(foodItem.getFoodItemName());
			updateFoodItem.setFoodItemPicture(foodItem.getFoodItemPicture());
			updateFoodItem.setFoodItemPrice(foodItem.getFoodItemPrice());
			foodItemRepo.save(updateFoodItem);
		}
		else
			throw new UniquoNotFoundException("id: " +id);
		
		return updateFoodItem;
	}
}