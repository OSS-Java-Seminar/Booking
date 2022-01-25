package com.lolekibolek.Booking.persistence.entities;

import java.time.Instant;

public class ErrorModel {
	
	private String status;
	private String error;

    public ErrorModel(String status, String error) {
    	this.status = status;
        this.error = error;
    }

    public Instant getTimestamp() {
    	Instant time = Instant.now();
    	return time;
    }
    
    public String getStatus() {
    	return status; 
    }
    
    public String getError() {
        return error;
    }
    
    public void setStatus(String status) {
    	this.status = status; 
    }

    public void setError(String error) {
        this.error = error;
    }
}
