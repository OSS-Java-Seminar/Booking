package com.lolekibolek.Booking.persistence.api.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import com.lolekibolek.Booking.persistence.entities.ErrorModel;


@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler ({
		UserNotFoundException.class,
		UserAlreadyExistException.class
	})
	
	@Nullable
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
    public final ResponseEntity<ErrorModel> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex instanceof UserNotFoundException) { 
        	HttpStatus status = HttpStatus.NOT_FOUND; 
        	UserNotFoundException unfe = (UserNotFoundException) ex;
            return handleUserNotFoundException(unfe, headers, status, request);
        }
        
        else if (ex instanceof UserAlreadyExistException) { 
        	HttpStatus status = HttpStatus.BAD_REQUEST	; 
        	UserAlreadyExistException uaee = (UserAlreadyExistException) ex;
            return handleUserAlreadyExistException(uaee, headers, status, request);
        }
        
        else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Unknown exception type: " + ex.getClass().getName());
            }

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }
	
	protected ResponseEntity<ErrorModel> handleUserNotFoundException(UserNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
			
			return handleExceptionInternal(ex, new ErrorModel(ex.getStatus(), ex.getMessage()), headers, status, request);
		}
	
	protected ResponseEntity<ErrorModel> handleUserAlreadyExistException(UserAlreadyExistException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleExceptionInternal(ex, new ErrorModel(ex.getStatus(), ex.getMessage()), headers, status, request);
	}
	
	 protected ResponseEntity<ErrorModel> handleExceptionInternal(Exception ex, @Nullable ErrorModel body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		 if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			 request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		 }

		 return new ResponseEntity<>(body, headers, status);
	}
	 
}