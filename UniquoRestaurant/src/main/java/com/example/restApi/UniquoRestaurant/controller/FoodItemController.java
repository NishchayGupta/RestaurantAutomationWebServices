package com.example.restApi.UniquoRestaurant.controller;

import java.sql.Timestamp;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restApi.UniquoRestaurant.entity.FoodItem;
import com.example.restApi.UniquoRestaurant.exception.EmptyListException;
import com.example.restApi.UniquoRestaurant.exception.ExceptionResponse;
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
		if(foodItemSaved != null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(new ExceptionResponse(new Timestamp(System.currentTimeMillis()), "Food Item Added Successfully", "", "OK"));
		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(new Timestamp(System.currentTimeMillis()), "Food Item Not Added Successfully", "", "FAIL"));
		}
	}
	
	@DeleteMapping("/foodItem/{foodItemId}")
	public ResponseEntity<Object> removeFoodItem(@PathVariable int foodItemId)
	{
		Optional<FoodItem> foodItemById = foodItemRepo.findById(foodItemId);
		if(!foodItemById.isPresent())
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(new Timestamp(System.currentTimeMillis()), "Food Item Not Deleted Successfully", "", "FAIL"));
		}
		else
		{
			foodItemRepo.deleteById(foodItemId);
			return ResponseEntity.status(HttpStatus.OK).body(new ExceptionResponse(new Timestamp(System.currentTimeMillis()), "Food Item Deleted Successfully", "", "OK"));
		}
	}
	
	@PutMapping("/foodItem/{id}")
	public ResponseEntity<Object> updateFoodItem(@PathVariable int id, @RequestBody FoodItem foodItem)
	{
		Optional<FoodItem> foodItemFetched = foodItemRepo.findById(id);
		FoodItem updateFoodItem = foodItemFetched.get();
		if(foodItemFetched.isPresent())
		{
			updateFoodItem.setFoodItemName(foodItem.getFoodItemName());
			updateFoodItem.setFoodItemPicture(foodItem.getFoodItemPicture());
			updateFoodItem.setFoodItemPrice(foodItem.getFoodItemPrice());
			if(foodItemRepo.save(updateFoodItem) != null)
			{
				return ResponseEntity.status(HttpStatus.OK).body(new ExceptionResponse(new Timestamp(System.currentTimeMillis()), "Food Item Updated Successfully", "", "OK"));
			}
			else
			{
				throw new UniquoNotFoundException("id: " + id);
			}
		}
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(new Timestamp(System.currentTimeMillis()), "Food Item Not Updated Successfully", "", "FAIL"));
	}
}