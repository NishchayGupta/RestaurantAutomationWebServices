package com.example.restApi.UniquoRestaurant.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class OrderFoodItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderFoodItemId;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "foodItemOrder", nullable = false)
	private FoodItem foodItem;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderItem_orderFood", nullable = false)
	private OrderFood orderFood;
	private int quantity;
	
	public OrderFoodItem()
	{
		
	}
	
	public OrderFoodItem(int orderFoodItemId, int quantity) {
		super();
		this.orderFoodItemId = orderFoodItemId;
		this.quantity = quantity;
	}

	public int getOrderFoodItemId() {
		return orderFoodItemId;
	}

	public void setOrderFoodItemId(int orderFoodItemId) {
		this.orderFoodItemId = orderFoodItemId;
	}

	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	public OrderFood getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFood = orderFood;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}