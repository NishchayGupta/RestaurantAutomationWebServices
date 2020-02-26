package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

public class FoodItemOrderId implements Serializable{
	private int orderFood;
	private int foodItem;
	
	public FoodItemOrderId()
	{
		
	}

	public FoodItemOrderId(int orderFood, int foodItem) {
		super();
		this.orderFood = orderFood;
		this.foodItem = foodItem;
	}

	public int getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(int orderFood) {
		this.orderFood = orderFood;
	}

	public int getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(int foodItem) {
		this.foodItem = foodItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + foodItem;
		result = prime * result + orderFood;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodItemOrderId other = (FoodItemOrderId) obj;
		if (foodItem != other.foodItem)
			return false;
		if (orderFood != other.orderFood)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FoodItemOrderId [orderFood=" + orderFood + ", foodItem=" + foodItem + "]";
	}
}