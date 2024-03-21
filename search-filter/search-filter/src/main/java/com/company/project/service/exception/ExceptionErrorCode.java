package com.company.project.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionErrorCode {
    INVALID_INPUT("Invalid input", Category.BUSINESS),

    RESOURCES_NOT_FOUND("Resources not found", Category.BUSINESS),
    RESOURCES_EXISTS("Resources already exists", Category.BUSINESS),
    ACTIVE_CAMPAIGN("There are active campaign running", Category.BUSINESS),
    SOMETHING_WENT_WRONG("Something went wrong", Category.TECHNICAL);


    private final String shortName;
    private final Category category;

    @Getter
    @RequiredArgsConstructor
    public enum Category {

        BUSINESS("business"),
        TECHNICAL("technical");

        private final String shortName;

    }
}
