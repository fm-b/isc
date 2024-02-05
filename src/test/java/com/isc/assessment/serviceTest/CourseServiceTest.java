package com.isc.assessment.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.isc.assessment.model.Course;
import com.isc.assessment.repository.CourseRepository;
import com.isc.assessment.service.CourseService;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void testGetAllCourses() {
        Course course = new Course();
        course.setId(1L);
        course.setName("Java");
        Course course1 = new Course();
        course1.setId(2L);
        course1.setName("Python");
        Course course2 = new Course();
        course2.setId(3L);
        course2.setName("C++");

        List<Course> courses = Arrays.asList(course, course1, course2);

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertEquals(3, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("Python", result.get(1).getName());
        assertEquals("C++", result.get(2).getName());

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testGetCourseById() {
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        course.setName("Java");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(courseId);

        assertEquals("Java", result.getName());

        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    public void testSaveCourse() {
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        course.setName("Java");
        when(courseRepository.save(course)).thenReturn(course);
        Course result = courseService.saveCourse(course);
        assertEquals("Java", result.getName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testDeleteCourse() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        courseService.deleteCourse(courseId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    public void testDeleteCourseNotFound() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        CourseNotFoundException exception = assertThrows(
                CourseNotFoundException.class,
                () -> courseService.deleteCourse(courseId));

        assertTrue(exception.getMessage().contains("Course not found"));
        verify(courseRepository, never()).deleteById(courseId);
    }
}
