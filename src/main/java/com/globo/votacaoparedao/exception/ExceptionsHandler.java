package com.globo.votacaoparedao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.globo.votacaoparedao.entity.Error;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<Error> handleBusinessException(BusinessException ex){
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Error> handleNotFoundException(NotFoundException ex){
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IntegrationException.class)
    protected ResponseEntity<Error> handleIntegrationException(IntegrationException ex){
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
