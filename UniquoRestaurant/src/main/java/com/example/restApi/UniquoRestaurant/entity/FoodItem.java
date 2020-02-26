package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class FoodItem implements Serializable{
	
	private int id;
	private String foodItemName;
	private double foodItemPrice;
	private String foodItemPicture;
	
    private List<FoodItemOrder> foodItemOrders;
	
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
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL)
	public List<FoodItemOrder> getFoodItemOrders() {
		return foodItemOrders;
	}

	public void setFoodItemOrders(List<FoodItemOrder> foodItemOrders) {
		this.foodItemOrders = foodItemOrders;
	}
}