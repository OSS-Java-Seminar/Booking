package com.lolekibolek.Booking.persistence.api.exception;

public class UserAlreadyExistException extends Exception {

	private String email;

    public static UserAlreadyExistException createWith(String email) {
        return new UserAlreadyExistException(email);
    }

    public UserAlreadyExistException(String email) {
        this.email = email;
    }

    public String getStatus() {
    	return "504 EMAIL ALREADY EXISTS"; 
    }
    
    public String getMessage() {
        return "email " + email + " already exists";
    }
}
