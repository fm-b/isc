package com.isc.assessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InstructorNotFoundException.class})
    public ResponseEntity<Object> handleInstructorNotFoundException(InstructorNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
    
    @ExceptionHandler({CourseNotFoundException.class})
    public ResponseEntity<Object> handleCourseFoundException(CourseNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
    @ExceptionHandler({StudentNotFoundException.class})
    public ResponseEntity<Object> handleStudentFoundException(StudentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({CourseNotAssociatedToInstructorException.class})
    public ResponseEntity<Object> handleCourseNotAssociatedToInstructorException(CourseNotAssociatedToInstructorException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    // @ExceptionHandler({StudentAlreadyExistsException.class})
    // public ResponseEntity<Object> handleStudentAlreadyExistsException(StudentAlreadyExistsException exception) {
    //     return ResponseEntity
    //             .status(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .body(exception.getMessage());
    // }
    // @ExceptionHandler({RuntimeException.class})
    // public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
    //     return ResponseEntity
    //             .status(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .body(exception.getMessage());
    // }
}