package com.isc.assessment.dto;

public class CoursesByInstructorsDto {
    private Long instructorId;
    private Long studentId;
    public CoursesByInstructorsDto(Long instructorId,Long studentId) {
        this.instructorId = instructorId;
        this.studentId = studentId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
}
