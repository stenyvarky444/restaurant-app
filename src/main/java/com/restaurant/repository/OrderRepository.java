package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.CustOrder;

public interface OrderRepository extends JpaRepository<CustOrder, Long> {

}
