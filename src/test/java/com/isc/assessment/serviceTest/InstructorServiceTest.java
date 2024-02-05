package com.isc.assessment.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.isc.assessment.exception.CourseNotFoundException;
import com.isc.assessment.exception.InstructorNotFoundException;
import com.isc.assessment.model.Course;
import com.isc.assessment.model.Instructor;
import com.isc.assessment.repository.CourseRepository;
import com.isc.assessment.repository.InstructorRepository;
import com.isc.assessment.service.InstructorService;

@ExtendWith(MockitoExtension.class)
public class InstructorServiceTest {
    @InjectMocks
    private InstructorService instructorService;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void testGetAllInstructors() {
        Instructor instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("John Doe");
        Instructor instructor1 = new Instructor();
        instructor1.setId(2L);
        instructor1.setName("Jane Smith");
        Instructor instructor2 = new Instructor();
        instructor2.setId(3L);
        instructor2.setName("Bob Johnson");

        List<Instructor> instructors = Arrays.asList(instructor, instructor1, instructor2);

        when(instructorRepository.findAll()).thenReturn(instructors);

        List<Instructor> result = instructorService.getAllInstructors();

        assertEquals(3, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        assertEquals("Bob Johnson", result.get(2).getName());

        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    public void testGetInstructorById() {
        Long instructorId = 1L;
        Instructor instructor = new Instructor();
        instructor.setId(instructorId);
        instructor.setName("John Doe");
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        Instructor result = instructorService.getInstructorById(instructorId);

        assertEquals("John Doe", result.getName());

        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    public void testSaveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("John Doe");
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorService.saveInstructor(instructor);

        assertEquals("John Doe", result.getName());

        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testDeleteInstructor() {
        Long instructorId = 1L;
     when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(new Instructor()));
     instructorService.deleteInstructor(instructorId);
     verify(instructorRepository, times(1)).findById(instructorId);
     verify(instructorRepository, times(1)).deleteById(instructorId);
    }

    @Test
    public void testDeleteInstructorNotFound() {
        Long instructorId = 1L;

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.empty());

        InstructorNotFoundException exception = assertThrows(
                InstructorNotFoundException.class,
                () -> instructorService.deleteInstructor(instructorId));

        assertTrue(exception.getMessage().contains("Instructor not found"));

        verify(instructorRepository, never()).deleteById(instructorId);
    }

    @Test
    public void testAddCourseForInstructorInstructorNotFound() {
        Long instructorId = 1L;
        Long courseId = 2L;

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.empty());

        InstructorNotFoundException exception = assertThrows(
                InstructorNotFoundException.class,
                () -> instructorService.addCourseForInstructor(instructorId, courseId));

        assertTrue(exception.getMessage().contains("Instructor not found"));

        verify(courseRepository, never()).findById(any());
    }

    @Test
    public void testAddCourseForInstructorCourseNotFound() {
        Long instructorId = 1L;
        Long courseId = 2L;

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(new Instructor()));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        CourseNotFoundException exception = assertThrows(
                CourseNotFoundException.class,
                () -> instructorService.addCourseForInstructor(instructorId, courseId));

        assertTrue(exception.getMessage().contains("Course not found."));

    }

    @Test
    public void testAddCourseForInstructor() {
        Long instructorId = 1L;
        Long courseId = 2L;

        Instructor instructor = new Instructor();
        instructor.setId(instructorId);
        instructor.setName("John Doe");

        Course course = new Course();
        course.setId(courseId);
        course.setName("Java Programming");

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        instructorService.addCourseForInstructor(instructorId, courseId);

        verify(instructorRepository, times(1)).save(instructor);
    }
}
