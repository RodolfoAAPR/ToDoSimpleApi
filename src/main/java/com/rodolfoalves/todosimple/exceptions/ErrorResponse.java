package com.rodolfoalves.todosimple.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private String stackTrace;
    private List<ValidationError> errors;


    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError{
        private final String field;
        private final String message;

        private ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }

    public void addValidationError(String field, String message){
        if (Objects.isNull(errors)){
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ValidationError(field, message));
    }
}
