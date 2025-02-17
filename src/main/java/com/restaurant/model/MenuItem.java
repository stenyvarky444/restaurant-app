package com.restaurant.model;

import java.math.BigDecimal;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private Integer availableQuantity;

    @Enumerated(EnumType.STRING)
    private Category category;  // Enum for category (APPETIZER, MAIN_COURSE, DESSERT)

    // Constructors, getters, and setters
    public MenuItem() {}

    public MenuItem(String name, BigDecimal price, Integer availableQuantity, Category category) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.category = category;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

    
}

