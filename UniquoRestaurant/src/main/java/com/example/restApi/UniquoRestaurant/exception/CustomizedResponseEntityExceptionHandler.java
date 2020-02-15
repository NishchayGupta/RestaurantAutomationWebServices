package com.example.restApi.UniquoRestaurant.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), "Error");
		return new ResponseEntity(exceptionRes, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UniquoNotFoundException.class)
	public final ResponseEntity<Object> handleIdNotFoundException(UniquoNotFoundException ex, WebRequest request) {
		
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), "Error");
		return new ResponseEntity(exceptionRes, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), "Validation not valid", ex.getBindingResult().toString(), "Error");
		return new ResponseEntity(exceptionRes, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmptyListException.class)
	public final ResponseEntity<Object> handleIdNotFoundException(EmptyListException ex, WebRequest request) {
		
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), "Error");
		return new ResponseEntity(exceptionRes, HttpStatus.NOT_FOUND);
	}
}