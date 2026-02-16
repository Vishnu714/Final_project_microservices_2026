package com.example.aggregator.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handle(Exception ex) {
		return ResponseEntity.internalServerError()
				.body("Aggregator error: " + ex.getMessage());
	}
}
