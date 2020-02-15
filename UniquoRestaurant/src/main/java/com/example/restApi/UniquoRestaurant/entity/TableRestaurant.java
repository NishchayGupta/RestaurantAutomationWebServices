package com.example.restApi.UniquoRestaurant.entity;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name= "TableRestaurant")
public class TableRestaurant implements Serializable{
	@Id
	private int tableId;
	private Date bookingDateTime;
	private Date startDateTime;
	private Date endDateTime;
	private int waitingTime;
	
	private OrderFood orderFood;
	
	public TableRestaurant()
	{
		
	}

	public TableRestaurant(int tableId, Date bookingDateTime, Date startDateTime, Date endDateTime) {
		super();
		this.tableId = tableId;
		this.bookingDateTime = bookingDateTime;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public Date getBookingDateTime() {
		return bookingDateTime;
	}

	public void setBookingDateTime(Date bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "table", cascade = CascadeType.ALL)
	public OrderFood getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFood = orderFood;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
}