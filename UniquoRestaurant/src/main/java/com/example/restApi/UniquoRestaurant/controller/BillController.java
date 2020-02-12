package com.example.restApi.UniquoRestaurant.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restApi.UniquoRestaurant.entity.Bill;
import com.example.restApi.UniquoRestaurant.repository.BillRepository;

@RestController
@RequestMapping("/uniquo")
public class BillController {

	@Autowired
	private BillRepository billRepo;
	
	@GetMapping("/bills")
	public List<Bill> getAllBills()
	{
		return null;
	}
}