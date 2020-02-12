package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_customer")
	@JsonIgnore
	private Person personCustomer;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
	private OrderFood orderFood;
	
	public Customer()
	{
	}
	
	public Customer(Person person) 
	{
		this.personCustomer = person;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Person getPersonCustomer() {
		return personCustomer;
	}

	public void setPersonCustomer(Person personCustomer) {
		this.personCustomer = personCustomer;
	}

	public OrderFood getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFood = orderFood;
	}
}