package com.isc.assessment.dto;

public class ChooseCourseDto {
    private Long instructorId;
    private Long courseId;
    private Long studentId;
    public ChooseCourseDto(Long instructorId, Long courseId,Long studentId) {
        this.instructorId = instructorId;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public Long getStudentId() {
        return studentId;
    }


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
}
