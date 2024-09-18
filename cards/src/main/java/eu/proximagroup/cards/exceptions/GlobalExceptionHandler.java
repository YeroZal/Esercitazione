package eu.proximagroup.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import eu.proximagroup.cards.dto.ResponseErrorDto;
import jakarta.servlet.http.HttpServletRequest;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseErrorDto<String>> handlResourceNotFoundException(ResourceNotFoundException exception,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseErrorDto<String>(request.getRequestURI(),
				request.getMethod(), HttpStatus.NOT_FOUND, exception.getMessage()));
	
}
}
