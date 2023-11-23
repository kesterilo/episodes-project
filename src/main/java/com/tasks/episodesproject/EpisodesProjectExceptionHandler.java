package com.tasks.episodesproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tasks.episodesproject.exceptions.EntityNotFoundException;
import com.tasks.episodesproject.exceptions.ErrorResponse;

@ControllerAdvice
public class EpisodesProjectExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler({EntityNotFoundException.class, RuntimeException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException e) {
    ErrorResponse error = new ErrorResponse(Arrays.asList(e.getMessage()));
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    ErrorResponse error = new ErrorResponse(Arrays.asList("Data Integrity Violation: we cannot process your request."));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
  
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
    return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
  }
}
