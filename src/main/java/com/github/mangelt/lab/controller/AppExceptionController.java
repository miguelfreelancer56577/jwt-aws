package com.github.mangelt.lab.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.mangelt.lab.dto.FieldErrorDTO;
import com.github.mangelt.lab.dto.ReponseBodyDTO;
import com.github.mangelt.lab.exception.AppException;
import com.github.mangelt.lab.util.ApiConstants;

@ControllerAdvice
public class AppExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {AppException.class})
	ResponseEntity<Object> handleAppExceptions(AppException ex, WebRequest request) {
		return handleExceptionInternal(ex, ReponseBodyDTO
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.content(ex.getCause())
				.build(), 
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value = {AuthenticationException.class})
	ResponseEntity<Object> handleAppExceptions(AuthenticationException ex, WebRequest request) {
		return handleExceptionInternal(ex, ReponseBodyDTO
				.builder()
				.status(HttpStatus.FORBIDDEN.value())
				.message(ex.getMessage())
				.build(), 
				new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	ResponseEntity<Object> handleValidationPayloadExceptions(
			ConstraintViolationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ReponseBodyDTO
        		.builder()
        		.status(HttpStatus.BAD_REQUEST.value())
        		.message(ApiConstants.EXP_VALIDATION_FIELDS)
        		.content(this.getFields(ex))
        		.build(), 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
	
	List<FieldErrorDTO> getFields(RuntimeException ex){
		final List<FieldErrorDTO> fields = new ArrayList<>();
		final String[] elements = ex.getMessage().split(ApiConstants.SIGN_COMMA);
//		if there is no more than one element then extract the unique record.
		if(elements.length == 0) {
			fields.add(getField(ex.getMessage()));
		}else {
			fields.addAll(Arrays
							.stream(elements)
							.map(this::getField)
							.collect(Collectors.toList()));
		}
		return fields;
	}
	
	FieldErrorDTO getField(String inputField) {
		final String[] arrFragPro;
		final String[] chain = inputField.split(ApiConstants.SIGN_COLON);
		String fieldName = chain[0];
		if(fieldName.contains(ApiConstants.SIGN_DOT)) {
			arrFragPro = fieldName.split(ApiConstants.SIGN_SACAPED_DOT);
			fieldName = arrFragPro[arrFragPro.length-1];
		}
		final String fieldMessage = chain[1];
		return FieldErrorDTO
			.builder()
			.fieldMessage(fieldMessage)
			.fieldName(fieldName)
			.build();
	}
	
}
