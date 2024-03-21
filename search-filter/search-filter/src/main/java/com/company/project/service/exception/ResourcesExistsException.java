package com.company.project.service.exception;

public class ResourcesExistsException extends ApplicationException {

    public ResourcesExistsException(String message) {
        super(message);
    }

    @Override
    public ExceptionErrorCode code() {
        return ExceptionErrorCode.RESOURCES_EXISTS;
    }
}
