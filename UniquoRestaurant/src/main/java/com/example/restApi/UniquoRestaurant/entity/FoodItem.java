package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class FoodItem implements Serializable{
	private int id;
	private String foodItemName;
	private double foodItemPrice;
	private String foodItemPicture;
	
	
	private List<OrderFood> orderFood;
	
	public FoodItem()
	{
	}

	public FoodItem(String foodItemName, double foodItemPrice, String foodItemPicture) {
		super();
		this.foodItemName = foodItemName;
		this.foodItemPrice = foodItemPrice;
		this.foodItemPicture = foodItemPicture;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getFoodItemId() {
		return id;
	}

	public void setFoodItemId(int foodItemId) {
		this.id = foodItemId;
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
	@ManyToMany(mappedBy = "foodItems", cascade = CascadeType.ALL)
	public List<OrderFood> getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(List<OrderFood> orderFood) {
		this.orderFood = orderFood;
	}
}