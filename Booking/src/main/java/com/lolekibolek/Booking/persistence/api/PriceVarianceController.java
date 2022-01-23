package com.lolekibolek.Booking.persistence.api;

import com.lolekibolek.Booking.persistence.entities.PriceVariance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.repositories.PriceVarianceRepository;
import com.lolekibolek.Booking.persistence.services.PriceVarianceService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/price-variance")
@AllArgsConstructor
public class PriceVarianceController {

	private final PriceVarianceService priceVarianceService;
	//private final PriceVarianceRepository priceVarianceRepository;
	
	@Autowired
	PriceVarianceController (PriceVarianceService priceVarianceServices) {
		this.priceVarianceService = priceVarianceServices;
	}
	
	@GetMapping("/{id}")
	public PriceVariance findById(int id){
        return priceVarianceService.findById(id);
    }
	
	@GetMapping
    public List<PriceVariance> findAll() {
        return priceVarianceService.findAll();
    }
	
	@DeleteMapping
	public void deleteById(int id){
        priceVarianceService.deleteById(id);
    }
	
	@PostMapping
	public PriceVariance save(PriceVariance pv){
        return priceVarianceService.save(pv);
    }
}
