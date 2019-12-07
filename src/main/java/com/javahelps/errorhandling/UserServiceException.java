package com.javahelps.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
@ControllerAdvice
public class UserServiceException extends Exception {
	
	private Exception exception;
	
	public UserServiceException(Exception ex) {
        super(ex.getMessage());
    }
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
//		if (exception. == "org.springframework.dao.DataIntegrityViolationException")
		return super.getMessage();
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}
}