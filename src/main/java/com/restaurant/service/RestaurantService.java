package com.restaurant.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurant.model.Category;
import com.restaurant.model.CustOrder;
import com.restaurant.model.MenuItem;
import com.restaurant.model.OrderItem;
import com.restaurant.model.OrderStatus;
import com.restaurant.repository.MenuItemRepository;
import com.restaurant.repository.OrderRepository;

@Service
public class RestaurantService {
	
	@Autowired
	MenuItemRepository menuItemRepository;
	
	@Autowired
	OrderRepository orderRepository;

	public ResponseEntity<?> createMenuItem(MenuItem menuItem) {
		// Validate price (should be positive)
	    if (menuItem.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
	        // Return a bad request response with an error message
	        return ResponseEntity.badRequest().body("Price must be positive");
	    }
	    
	    // Save the menu item if everything is valid
	    menuItemRepository.save(menuItem);
	    
	    // Return a created response with the saved menu item
	    return ResponseEntity.status(HttpStatus.CREATED).body(menuItem); 
	}

	public ResponseEntity<List<MenuItem>> getMenuItems(Category category) {
		List<MenuItem> menuItems;
	    if (category != null) {
	        menuItems = menuItemRepository.findByCategory(category);
	    } else {
	        menuItems = menuItemRepository.findAll();
	    }
	    return ResponseEntity.ok(menuItems);
	}

	public ResponseEntity<?> createOrder(CustOrder order) {
		BigDecimal totalAmount = BigDecimal.ZERO;

//		 MenuItem menuItems = menuItemRepository.findById(order.getItems().get(0).getId()).get();
	    // Validate items and calculate total amount
		int j=0;
	    for (OrderItem item : order.getItems()) {
	    	MenuItem menuItems = menuItemRepository.findById(order.getItems().get(j).getId()).get();
	    	item.setMenuItem(menuItems);
	        if (item.getQuantity() > item.getMenuItem().getAvailableQuantity()) {
	            // Return a bad request response with an error message (ResponseEntity<String>)
	            return ResponseEntity.badRequest().body("Insufficient stock for item: " + item.getMenuItem().getName());
	        }
	        item.setSubtotal(item.getMenuItem().getPrice().multiply(new BigDecimal(item.getQuantity())));
	        totalAmount = totalAmount.add(item.getSubtotal());
	        j++;
	    }

	    // Update inventory
	    for (OrderItem item : order.getItems()) {
	        MenuItem menuItem = item.getMenuItem();
	        menuItem.setAvailableQuantity(menuItem.getAvailableQuantity() - item.getQuantity());
	        menuItemRepository.save(menuItem);
	    }

	    // Save the order
	    order.setTotalAmount(totalAmount);
	    order.setStatus(OrderStatus.RECEIVED);
	    order.setCreatedAt(LocalDateTime.now());
	    orderRepository.save(order);

	    // Return a 201 Created response with the order (ResponseEntity<Order>)
	    return ResponseEntity.status(HttpStatus.CREATED).body(order);
	}

	public ResponseEntity<?> getOrderById(Long id) {
Optional<CustOrder> order = orderRepository.findById(id);
	    
	    if (order.isPresent()) {
	        // If order is found, return it with a 200 OK status
	        return ResponseEntity.ok(order.get());
	    } else {
	        // If order is not found, return a 404 Not Found status with an error message
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
	    }
	}

}
