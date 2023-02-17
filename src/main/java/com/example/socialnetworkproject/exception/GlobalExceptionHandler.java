package com.example.socialnetworkproject.exception;

import com.example.socialnetworkproject.models.entities.DTO.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailExistException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAccountExistException(EmailExistException ex, WebRequest request) {
        StringBuilder message = new StringBuilder();
        message.append("Email ").append(ex.getEmail()).append(" already exists !!!");
        return new ErrorMessage(400, message);
    }

}
