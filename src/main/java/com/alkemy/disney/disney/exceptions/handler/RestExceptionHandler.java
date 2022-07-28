package com.alkemy.disney.disney.exceptions.handler;

import com.alkemy.disney.disney.dto.APIErrorDTO;
import com.alkemy.disney.disney.exceptions.EmailExc;
import com.alkemy.disney.disney.exceptions.ExistentUserExc;
import com.alkemy.disney.disney.exceptions.NonExistentUserExc;
import com.alkemy.disney.disney.exceptions.ParamNotFoundExc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice // Class in charge of handling API Exceptions
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {ParamNotFoundExc.class}) // Parameter not found
    protected ResponseEntity<APIErrorDTO<String>> handleParamNotFound(RuntimeException e, WebRequest request) {

        return new ResponseEntity<>(
                new APIErrorDTO<>(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailExc.class) // Email errors
    protected ResponseEntity<APIErrorDTO<String>> handleEmailExc(EmailExc e) {
        return new ResponseEntity<>(
                new APIErrorDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExistentUserExc.class) // Already registered user trying to register again
    protected ResponseEntity<APIErrorDTO<String>> handleExistentUser(ExistentUserExc e) {
        return new ResponseEntity<>(
                new APIErrorDTO<>(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonExistentUserExc.class) // Not registered user trying to log in
    protected ResponseEntity<APIErrorDTO<String>> handleNonExistentUser(ExistentUserExc e) {
        return new ResponseEntity<>(
                new APIErrorDTO<>(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @Override // Method argument not valid
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        // Once all the errors are obtained for invalid args, an APIError is created and returned
        return handleExceptionInternal(ex, new APIErrorDTO<>(HttpStatus.BAD_REQUEST, errors), headers, status, request);
    }


}
