package com.example.socialnetworkproject.exception;

public class EmailExistException extends RuntimeException{
    String email;
    public EmailExistException(String email){
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
