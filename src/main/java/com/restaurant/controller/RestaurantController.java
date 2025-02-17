package com.restaurant.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Category;
import com.restaurant.model.CustOrder;
import com.restaurant.model.MenuItem;
import com.restaurant.service.RestaurantService;

@RestController
public class RestaurantController {

	@Autowired
	RestaurantService restaurantService;
	
	@PostMapping("/api/menu-items")
	public ResponseEntity<?> createMenuItem(@RequestBody MenuItem menuItem) {
		return restaurantService.createMenuItem(menuItem);
	}

	
	@GetMapping("/api/menu-items")
	public ResponseEntity<List<MenuItem>> getMenuItems(@RequestParam Category category) {
		return restaurantService.getMenuItems(category);
	}
	
	@PostMapping("/api/orders")
	public ResponseEntity<?> createOrder(@RequestBody CustOrder order) {
		return restaurantService.createOrder(order);
	}
	
	@GetMapping("/api/orders/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable Long id) {
		return restaurantService.getOrderById(id);
	}




}
