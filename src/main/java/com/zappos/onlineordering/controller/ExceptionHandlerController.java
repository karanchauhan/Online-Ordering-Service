// package com.zappos.onlineordering.controller;
//
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.converter.HttpMessageNotReadableException;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.context.request.WebRequest;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import
// org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
// import com.zappos.onlineordering.model.ApiException;
//
// @ControllerAdvice
// @EnableWebMvc
// public class ExceptionHandlerController extends
// ResponseEntityExceptionHandler {
//
// @ExceptionHandler({ ApiException.class })
// public ResponseEntity<Object> handleApiException(ApiException ex) {
//
// return new ResponseEntity<Object>(ex, ex.getStatus());
// }
//
// @ExceptionHandler({ Exception.class })
// public ResponseEntity<Object> handleApiException(Exception ex) {
//
// ApiException apiEx = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
// ex.getMessage());
// return new ResponseEntity<Object>(apiEx, HttpStatus.INTERNAL_SERVER_ERROR);
// }
//
// @Override
// protected ResponseEntity<Object>
// handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
// HttpHeaders headers, HttpStatus status, WebRequest request) {
// return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
// }
//
// }
