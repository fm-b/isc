package com.isc.assessment.controller;

import org.springframework.web.bind.annotation.RestController;

import com.isc.assessment.dto.AddCourseDto;
import com.isc.assessment.model.Instructor;
import com.isc.assessment.service.InstructorService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/instructors")

public class InstructorController {
    
    
   
    @Autowired
    InstructorService instructorService;


    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = instructorService.getAllInstructors();
        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long instructorId) {
        Instructor instructor = instructorService.getInstructorById(instructorId);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
        Instructor createdInstructor = instructorService.saveInstructor(instructor);
        return new ResponseEntity<>(createdInstructor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long instructorId) {
        instructorService.deleteInstructor(instructorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/add-course-for-instructor")
    public ResponseEntity<String> addCourseForInstructor(@RequestBody AddCourseDto addCourseDto) {
        instructorService.addCourseForInstructor(addCourseDto.getInstructorId(), addCourseDto.getCourseId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
