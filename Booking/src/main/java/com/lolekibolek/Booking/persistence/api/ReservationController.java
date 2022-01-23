package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.services.ApartmentService;
import com.lolekibolek.Booking.persistence.services.ReservationService;

public class ReservationController {

private final ReservationService reservationService;
	
	@Autowired
	ReservationController (ReservationService reservationServices) {
		this.reservationService = reservationServices;
	}
	
	@GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }
	
	@GetMapping("/{id}")
    public Reservation findById(int id) {
        return reservationService.findById(id);
    }
}
