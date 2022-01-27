package com.lolekibolek.Booking.persistence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApartmentService {
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }
    
    public Apartment findById(int id) {
    	return apartmentRepository.findById(id);
    }

	public List<Apartment> findByCity(String city) {
		// TODO Auto-generated method stub
		return apartmentRepository.findByCity(city);
	}
}
