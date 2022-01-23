package com.lolekibolek.Booking.persistence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
    
    public Reservation findById(int id) {
    	return reservationRepository.findById(id);
    }
}
