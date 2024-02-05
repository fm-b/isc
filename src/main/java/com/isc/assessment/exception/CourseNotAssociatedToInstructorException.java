package com.isc.assessment.exception;

public class CourseNotAssociatedToInstructorException extends RuntimeException{
    public CourseNotAssociatedToInstructorException(String message){
        super(message);
    }
}
