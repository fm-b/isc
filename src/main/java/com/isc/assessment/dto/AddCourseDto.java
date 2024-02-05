package com.isc.assessment.dto;

public class AddCourseDto {
    private Long instructorId;
    private Long courseId;

    public AddCourseDto(Long instructorId, Long courseId) {
        this.instructorId = instructorId;
        this.courseId = courseId;
    }

    // Getters and setters
    public Long getInstructorId() {
        return instructorId;
    }



    public Long getCourseId() {
        return courseId;
    }


}
