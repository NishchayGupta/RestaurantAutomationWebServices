package com.example.restApi.UniquoRestaurant.dao;

public class CustomerOrderCart {
	private int foodItemId;
	private int quantity;
	
	public CustomerOrderCart()
	{
	}
	
	public CustomerOrderCart(int foodItemId, int quantity) {
		super();
		this.foodItemId = foodItemId;
		this.quantity = quantity;
	}

	public int getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(int foodItemId) {
		this.foodItemId = foodItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CustomerOrderCart [foodItemId=" + foodItemId + ", quantity=" + quantity + "]";
	}
}