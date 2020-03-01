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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Customer implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_customer")
	@JsonIgnore
	private Person personCustomer;
	private String orderType;
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<OrderFood> orderFood;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "table_customer", unique = false)
	private TableRestaurant tablesRestaurant;
	
	public Customer()
	{
	}

	public Customer(Person personCustomer, TableRestaurant tablesRestaurant, String orderType) {
		super();
		this.personCustomer = personCustomer;
		this.tablesRestaurant = tablesRestaurant;
		this.orderType = orderType;
	}

	public int getCustomerId() {
		return id;
	}

	public void setCustomerId(int customerId) {
		this.id = customerId;
	}

	public Person getPersonCustomer() {
		return personCustomer;
	}

	public void setPersonCustomer(Person personCustomer) {
		this.personCustomer = personCustomer;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<OrderFood> getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(List<OrderFood> orderFood) {
		this.orderFood = orderFood;
	}

	public TableRestaurant getTablesRestaurant() {
		return tablesRestaurant;
	}

	public void setTablesRestaurant(TableRestaurant tablesRestaurant) {
		this.tablesRestaurant = tablesRestaurant;
	}
}