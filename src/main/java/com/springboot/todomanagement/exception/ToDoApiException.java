package com.springboot.todomanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ToDoApiException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
}
