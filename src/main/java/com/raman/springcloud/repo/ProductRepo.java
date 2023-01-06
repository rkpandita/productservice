package com.raman.springcloud.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raman.springcloud.dto.Coupon;
import com.raman.springcloud.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	Coupon findByName(String name);
	
}
