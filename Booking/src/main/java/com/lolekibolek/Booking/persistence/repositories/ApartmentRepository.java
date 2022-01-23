package com.lolekibolek.Booking.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lolekibolek.Booking.persistence.entities.Apartment;

@Repository
@Component
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

    Apartment findById(int id);
}
