package com.fleetmanagement.api_rest.business.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.security.InvalidParameterException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse("The requested endpoint does not exist: " + ex.getRequestURL()
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValueNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleValueNotFoundException(ValueNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Value not found. " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidFormatException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Invalid format. " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		ErrorResponse errorResponse = new ErrorResponse("User already exists with email: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatchException(TypeMismatchException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Invalid parameter. Page and limit must be valid integers");
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Invalid parameter." + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse("An error occurred. " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
