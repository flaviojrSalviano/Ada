package com.ada.controller;

import java.io.IOException;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ada.response.Response;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, MethodArgumentTypeMismatchException.class, InvalidDataAccessApiUsageException.class, IOException.class})
    public final ResponseEntity<Response<String>> handleAllExceptions() {
    	Response<String> response = new Response<String>();
		response.getErrors().add("Não foi possível processar esta solicitação");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

