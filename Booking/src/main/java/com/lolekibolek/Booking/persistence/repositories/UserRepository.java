package com.lolekibolek.Booking.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lolekibolek.Booking.persistence.entities.User;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);
    User findById(int id);
    User save(User newUser);
	User findByUsername(String username);
}
