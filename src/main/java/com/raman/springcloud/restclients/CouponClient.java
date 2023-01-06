package com.raman.springcloud.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.raman.springcloud.dto.Coupon;

// @FeignClient("GATEWAY-SERVICE")
@FeignClient("COUPON-SERVICE")
public interface CouponClient {

	@GetMapping("couponapi/coupon/{code}")
	Coupon getCoupon(@PathVariable("code") String code);
	
}
