package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundIdException extends Exception {
    public ProductNotFoundIdException(String message) {
        super(message);
    }
}
