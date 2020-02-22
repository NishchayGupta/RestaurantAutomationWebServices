package com.example.restApi.UniquoRestaurant.dao;

import java.io.Serializable;
import java.sql.Timestamp;

import com.example.restApi.UniquoRestaurant.entity.Person;

public class PersonUser implements Serializable{
	
	private String status;
	private String timestamp;
	private String message;
	private Person person;
	
	public PersonUser()
	{
		
	}
	
	public PersonUser(String status, String message, Person person) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timeStamp();
		this.person = person;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp() {
		this.timestamp = timeStamp();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "PersonCustomer [status=" + status + ", timestamp=" + timestamp + ", message=" + message + ", person="
				+ person + "]";
	}
	
	public String timeStamp()
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tm = "" + ts.getTime();
        return tm;
    }
}