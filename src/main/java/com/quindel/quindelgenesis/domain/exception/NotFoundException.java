package com.quindel.quindelgenesis.domain.exception;

public class NotFoundException extends RuntimeException{

    private String errorCode;
    private String errorMessage;

    public NotFoundException(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode(){
        return errorCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
