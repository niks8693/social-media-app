package com.niks.rest.webservices.app.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.niks.rest.webservices.app.user.UserNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse exres=new ExceptionResponse(ex.getMessage(), new Date(), request.getDescription(false));
		return new ResponseEntity<Object>(exres, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		ExceptionResponse exres=new ExceptionResponse(ex.getMessage(), new Date(), request.getDescription(false));
		return new ResponseEntity<Object>(exres, HttpStatus.NOT_FOUND);
	}

}
