package com.isc.assessment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isc.assessment.dto.ChooseCourseDto;
import com.isc.assessment.dto.CoursesByInstructorsDto;
import com.isc.assessment.model.Course;
import com.isc.assessment.model.Student;
import com.isc.assessment.service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/choose-course-by-student")
    public ResponseEntity<String> chooseCourseForStudent(@RequestBody ChooseCourseDto chooseCourseDto) {

        studentService.chooseCourseForStudent(chooseCourseDto.getStudentId(), chooseCourseDto.getInstructorId(),
                chooseCourseDto.getCourseId());
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("get-instructor-courses-for-students")
    public ResponseEntity<List<Course>> getCoursesByInstructorForStudent(
            @RequestBody CoursesByInstructorsDto coursesByInstructorsDto) {

        List<Course> courses = studentService.getCoursesByInstructorForStudent(coursesByInstructorsDto.getStudentId(),
                coursesByInstructorsDto.getInstructorId());
        return ResponseEntity.ok(courses);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
