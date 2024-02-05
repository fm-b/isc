package com.isc.assessment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isc.assessment.exception.CourseNotAssociatedToInstructorException;
import com.isc.assessment.exception.CourseNotFoundException;
import com.isc.assessment.exception.InstructorNotFoundException;
import com.isc.assessment.exception.StudentNotFoundException;
import com.isc.assessment.model.Course;
import com.isc.assessment.model.Instructor;
import com.isc.assessment.model.Student;
import com.isc.assessment.repository.CourseRepository;
import com.isc.assessment.repository.InstructorRepository;
import com.isc.assessment.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    InstructorRepository instructorRepository;

    public List<Student> getAllStudents() {

        return studentRepository.findAll();

    }

    public Student getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        return student;
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        studentRepository.deleteById(id);
    }

    public void chooseCourseForStudent(Long studentId, Long instructorId, Long courseId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));

        boolean isAssociatedWithInstructor = instructor.getCourses().stream()
                .anyMatch(thisCourse -> thisCourse.getId() == courseId);

        if (isAssociatedWithInstructor) {

            Set<Course> courses = student.getCourses();
            courses.add(course);
            studentRepository.save(student);
        } else {
            throw new CourseNotAssociatedToInstructorException(
                    "The course is not associated with the given instructor.");
        }
    }

    public List<Course> getCoursesByInstructorForStudent(Long studentId, Long instructorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found"));

        return new ArrayList<>(student.getCourses().stream()
                .filter(instructor.getCourses()::contains)
                .collect(Collectors.toSet()));
    }

}
