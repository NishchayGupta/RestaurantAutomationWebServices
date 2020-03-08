package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@IdClass(FoodItemOrderId.class)
public class FoodItemOrder implements Serializable{
	@Id
    @ManyToOne
    @JoinColumn(name = "orderFood_id", referencedColumnName = "id")
	@JsonIgnore
    private OrderFood orderFood;

    @Id
    @ManyToOne
    @JoinColumn(name = "foodItem_id", referencedColumnName = "id")
    private FoodItem foodItem;

    private int quantity;

	public FoodItemOrder()
	{
		
	}
	
	public FoodItemOrder(FoodItem foodItem, int quantity) {
		super();
		this.foodItem = foodItem;
		this.quantity = quantity;
	}

	public OrderFood getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFood = orderFood;
	}

	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}