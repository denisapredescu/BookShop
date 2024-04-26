package com.awbd.bookshop.exceptions.handlers;

import com.awbd.bookshop.exceptions.exceptions.DeletedBookException;
import com.awbd.bookshop.exceptions.exceptions.EmailAlreadyUsedException;
import com.awbd.bookshop.exceptions.exceptions.NoFoundElementException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
@ControllerAdvice
public class ExceptionHandler extends BaseExceptionHandler {

@org.springframework.web.bind.annotation.ExceptionHandler(EmailAlreadyUsedException.class)
public ModelAndView handleEmailAlreadyUsedException(EmailAlreadyUsedException ex, WebRequest request) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.getModel().put("exception",ex);
    modelAndView.setViewName("sameEmailException");
    return modelAndView;
}

    @org.springframework.web.bind.annotation.ExceptionHandler(DeletedBookException.class)
    public ModelAndView handleDeletedBookException(DeletedBookException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception",ex);
        modelAndView.setViewName("deletedBookException");
        return modelAndView;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoFoundElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoSuchElementException(NoFoundElementException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception",ex);
        modelAndView.setViewName("notFoundException");
        return modelAndView;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return setResponseBody(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "Validation failed");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return setResponseBody(ex, request, HttpStatus.BAD_REQUEST, errors.toString());
    }

    private ResponseEntity<Object> setResponseBody(Exception ex,  WebRequest request, HttpStatus status, String error) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }
}
