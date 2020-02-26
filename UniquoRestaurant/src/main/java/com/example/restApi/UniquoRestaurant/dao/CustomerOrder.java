package com.example.restApi.UniquoRestaurant.dao;

import java.util.List;

public class CustomerOrder {
	private int customerId;
	private int tableId;
	private List<CustomerOrderCart> orderCart;
	
	public CustomerOrder()
	{
	}

	public CustomerOrder(int customerId, int tableId, List<CustomerOrderCart> orderCart) {
		super();
		this.customerId = customerId;
		this.tableId = tableId;
		this.orderCart = orderCart;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public List<CustomerOrderCart> getOrderCart() {
		return orderCart;
	}

	public void setOrderCart(List<CustomerOrderCart> orderCart) {
		this.orderCart = orderCart;
	}

	@Override
	public String toString() {
		return "CustomerOrder [customerId=" + customerId + ", tableId=" + tableId + ", orderCart=" + orderCart + "]";
	}
}