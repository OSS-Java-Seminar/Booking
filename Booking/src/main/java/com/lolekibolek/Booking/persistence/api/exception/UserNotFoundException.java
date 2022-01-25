package com.lolekibolek.Booking.persistence.api.exception;

import java.time.Instant;


public class UserNotFoundException extends Exception {
	
	private int id;

    public static UserNotFoundException createWith(int id) {
        return new UserNotFoundException(id);
    }

    private UserNotFoundException(int id) {
        this.id = id;
    }

    public String getStatus() {
    	return "404 ID NOT FOUND"; 
    }
    
    @Override
    public String getMessage() {
        return "id " + id + " not found";
    }
}