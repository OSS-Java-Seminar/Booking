package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.services.ApartmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/apartments")
@AllArgsConstructor
public class ApartmentController {

	private final ApartmentService apartmentService;
	
	@Autowired
	ApartmentController (ApartmentService apartmentServices) {
		this.apartmentService = apartmentServices;
	}
	
	@GetMapping
    public List<Apartment> findAll() {
        return apartmentService.findAll();
    }
	
	@GetMapping("/{id}")
    public Apartment findById(int id) {
        return apartmentService.findById(id);
    }
}
