package com.hpc.ship.handlers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hpc.ship.constants.HttpConstants;
import com.hpc.ship.dto.ErrorDto;
import com.hpc.ship.exceptions.InvalidPayloadException;
import com.hpc.ship.exceptions.ShipNotFoundException;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(InvalidPayloadException.class)
	public ResponseEntity<ErrorDto> handleInvalidPayloadException(Exception ex) {
		ex.printStackTrace();
		ErrorDto errorDto = new ErrorDto(
			HttpConstants.BAD_REQUEST_STATUS,
			HttpConstants.BAD_REQUEST_NAME,
			ex.getMessage()
		);
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorDto> handleConstraintViolationException(Exception ex) {
		ex.printStackTrace();

		String message = ex.getMessage();
		if(StringUtils.containsIgnoreCase(message, "ship(code)")) {
			message = "A ship with this code already exists, use another code";
		}

		ErrorDto errorDto = new ErrorDto(
			HttpConstants.BAD_REQUEST_STATUS,
			HttpConstants.BAD_REQUEST_NAME,
			message
		);
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ShipNotFoundException.class)
	public ResponseEntity<ErrorDto> handleShipNotFoundException(Exception ex) {
		ex.printStackTrace();
		ErrorDto errorDto = new ErrorDto(
			HttpConstants.NOT_FOUND_STATUS,
			HttpConstants.NOT_FOUND_NAME,
			ex.getMessage()
		);
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleDefaultException(Exception ex) {
		ex.printStackTrace();
		ErrorDto errorDto = new ErrorDto(
			HttpConstants.INTERNAL_SERVER_ERROR_STATUS,
			HttpConstants.INTERNAL_SERVER_ERROR_NAME,
			ex.getMessage()
		);
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
