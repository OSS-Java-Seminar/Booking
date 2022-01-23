package com.lolekibolek.Booking.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lolekibolek.Booking.persistence.entities.Review;

@Repository
@Component
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	Review findByReservation_id(int reservation_id);
	Review findById(int id);
}
