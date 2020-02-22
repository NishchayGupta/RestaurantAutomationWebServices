package com.example.restApi.UniquoRestaurant.dao;

import java.sql.Timestamp;
import java.util.List;
import com.example.restApi.UniquoRestaurant.entity.FoodItem;
import com.example.restApi.UniquoRestaurant.entity.Person;

public class CustomerOrder {
	private Person person;
	private List<FoodItem> itemFood;
	private List<Integer> foodQuantity;
	
	public CustomerOrder() {
	}

	public CustomerOrder(Person person, List<FoodItem> itemFood,
			List<Integer> foodQuantity) {
		super();
		this.person = person;
		this.itemFood = itemFood;
		this.foodQuantity = foodQuantity;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<FoodItem> getItemFood() {
		return itemFood;
	}

	public void setItemFood(List<FoodItem> itemFood) {
		this.itemFood = itemFood;
	}
	
	public List<Integer> getFoodQuantity() {
		return foodQuantity;
	}

	public void setFoodQuantity(List<Integer> foodQuantity) {
		this.foodQuantity = foodQuantity;
	}

	public String timeStamp()
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tm = "" + ts.getTime();
        return tm;
    }
}