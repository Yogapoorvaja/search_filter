package com.company.project.service.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private String message;

    public ApplicationException(String message){
        this.message = message;
    }

    public abstract ExceptionErrorCode code();
}
