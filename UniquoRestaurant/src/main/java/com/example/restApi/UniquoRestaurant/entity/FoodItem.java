package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class FoodItem implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int foodItemId;
	private String foodItemName;
	private double foodItemPrice;
	private String foodItemPicture;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "foodItem", cascade = CascadeType.ALL)
	private OrderFoodItem orderFoodItem;
	
	public FoodItem()
	{
	}

	public FoodItem(String foodItemName, double foodItemPrice, String foodItemPicture) {
		this.foodItemName = foodItemName;
		this.foodItemPrice = foodItemPrice;
		this.foodItemPicture = foodItemPicture;
	}

	public int getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(int foodItemId) {
		this.foodItemId = foodItemId;
	}

	public String getFoodItemName() {
		return foodItemName;
	}

	public void setFoodItemName(String foodItemName) {
		this.foodItemName = foodItemName;
	}

	public double getFoodItemPrice() {
		return foodItemPrice;
	}

	public void setFoodItemPrice(double foodItemPrice) {
		this.foodItemPrice = foodItemPrice;
	}

	public String getFoodItemPicture() {
		return foodItemPicture;
	}

	public void setFoodItemPicture(String foodItemPicture) {
		this.foodItemPicture = foodItemPicture;
	}

	public OrderFoodItem getOrderFoodItem() {
		return orderFoodItem;
	}

	public void setOrderFoodItem(OrderFoodItem orderFoodItem) {
		this.orderFoodItem = orderFoodItem;
	}
}