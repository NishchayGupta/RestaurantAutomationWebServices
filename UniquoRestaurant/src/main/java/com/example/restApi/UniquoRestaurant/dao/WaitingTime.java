package com.example.restApi.UniquoRestaurant.dao;

public class WaitingTime {

	private int tableNumber;
	private int waitingTimeInMinutes;
	
	public WaitingTime()
	{
		
	}
	
	public WaitingTime(int tableNumber, int waitingTimeInMinutes) {
		super();
		this.tableNumber = tableNumber;
		this.waitingTimeInMinutes = waitingTimeInMinutes;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getWaitingTimeInMinutes() {
		return waitingTimeInMinutes;
	}

	public void setWaitingTimeInMinutes(int waitingTimeInMinutes) {
		this.waitingTimeInMinutes = waitingTimeInMinutes;
	}
}