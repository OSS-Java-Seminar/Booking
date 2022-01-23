package com.lolekibolek.Booking.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lolekibolek.Booking.persistence.entities.PriceVariance;

@Repository
@Component
public interface PriceVarianceRepository extends JpaRepository<PriceVariance, Integer>{

	PriceVariance findById(int id);
}
