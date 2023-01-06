package com.raman.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.raman.springcloud.dto.Coupon;
import com.raman.springcloud.model.Product;
import com.raman.springcloud.repo.ProductRepo;
import com.raman.springcloud.restclient.CouponClient;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/productapi")
@RefreshScope
public class ProductRestController {

	@Autowired
	ProductRepo productRepo;

	@Autowired
	CouponClient couponClient;
	
	@Value("${com.raman.springcloud.prop}")
	private String prop;

	@RequestMapping(value = "/product/{name}", method = RequestMethod.GET)
	public Coupon getProduct(@PathVariable("name") String name) {
		return productRepo.findByName(name);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@Retry(name = "product-api", fallbackMethod = "handleError")
	public Product create(@RequestBody Product product) {
		Coupon coupon = couponClient.getCoupon(product.getCouponCode());
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
		return productRepo.save(product);
	}

	public Product handleError(Product product, Exception exception) {
		System.out.println("Inside Handle error");
		return product;
	}
	

	@RequestMapping(value = "/prop", method = RequestMethod.GET)
	public String getProp() {
		return this.prop;
	}
	
}
