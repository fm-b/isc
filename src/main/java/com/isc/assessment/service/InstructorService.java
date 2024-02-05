package com.isc.assessment.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isc.assessment.exception.CourseNotFoundException;
import com.isc.assessment.exception.InstructorNotFoundException;
import com.isc.assessment.model.Course;
import com.isc.assessment.model.Instructor;
import com.isc.assessment.repository.CourseRepository;
import com.isc.assessment.repository.InstructorRepository;

@Service
public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();

    }

    public Instructor getInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found"));
        return instructor;
    }

    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long instructorId) {
        instructorRepository.deleteById(instructorId);
    }

    public void addCourseForInstructor(Long instructorId, Long courseId) {

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));

        Set<Course> courses = instructor.getCourses();
        courses.add(course);
        instructor.setCourses(courses);
        instructorRepository.save(instructor);
    }

}
