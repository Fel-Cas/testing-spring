package com.api.rest.exceptions;

public class BadRequest extends RuntimeException{
    public  BadRequest(String message){
        super(message);
    }
}
