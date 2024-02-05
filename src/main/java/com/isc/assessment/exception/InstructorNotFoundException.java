package com.isc.assessment.exception;

public class InstructorNotFoundException extends RuntimeException{
    public InstructorNotFoundException(String message){
        super(message);
    }
}
