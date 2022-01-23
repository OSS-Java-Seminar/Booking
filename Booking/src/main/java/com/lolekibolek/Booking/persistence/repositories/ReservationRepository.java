package com.lolekibolek.Booking.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lolekibolek.Booking.persistence.entities.Reservation;

@Repository
@Component
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	Reservation findById(int id);
}
