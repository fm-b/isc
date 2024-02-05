package com.isc.assessment.serviceTest;

import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.isc.assessment.exception.CourseNotAssociatedToInstructorException;
import com.isc.assessment.exception.StudentNotFoundException;
import com.isc.assessment.model.Course;
import com.isc.assessment.model.Instructor;
import com.isc.assessment.model.Student;
import com.isc.assessment.repository.CourseRepository;
import com.isc.assessment.repository.InstructorRepository;
import com.isc.assessment.repository.StudentRepository;
import com.isc.assessment.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @Test
    public void testGetAllStudents() {
        Student student= new Student();
        student.setId(1L);
        student.setStudentNumber(11L);
        student.setName("Alice");
        Student student1= new Student();
        student1.setId(2L);
        student1.setStudentNumber(22L);
        student1.setName("Bob");
        Student student2= new Student();
        student2.setId(3L);
        student2.setStudentNumber(33L);
        student2.setName("Eve");
        List<Student> students = Arrays.asList(student, student1, student2);
        when(studentRepository.findAll()).thenReturn(students);
        List<Student> result = studentService.getAllStudents();
        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Eve", result.get(2).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentById() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setStudentNumber(3L);
        student.setName("Alice");
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Student result = studentService.getStudentById(studentId);
        assertEquals("Alice", result.getName());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setStudentNumber(2L);
        student.setName("Alice");
        studentService.addStudent(student);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testDeleteStudent() {
        Long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    public void testDeleteStudentStudentNotFound() {
        Long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                () -> studentService.deleteStudent(studentId));

        assertTrue(exception.getMessage().contains("Student not found"));

        verify(studentRepository, never()).deleteById(studentId);
    }

    @Test
    public void testChooseCourseForStudent() {
        Long studentId = 1L;
        Long instructorId = 2L;
        Long courseId = 3L;

        Student student = new Student();
        student.setId(studentId);
        student.setName("Alice");

        Instructor instructor = Mockito.mock(Instructor.class);
        instructor.setId(instructorId);
        instructor.setName("John Doe");

        Course course = new Course();
        course.setId(courseId);
        course.setName("Java Programming");

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Mockito.when(instructor.getCourses()).thenReturn(new HashSet<>(Arrays.asList(course)));

        studentService.chooseCourseForStudent(studentId, instructorId, courseId);

        assertTrue(student.getCourses().contains(course));
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }

    @Test
    public void testChooseCourseForStudentNotAssociated() {
        Long studentId = 1L;
        Long instructorId = 2L;
        Long courseId = 3L;

        Student student = new Student();
        student.setId(studentId);
        student.setName("Alice");

        Instructor instructor = Mockito.mock(Instructor.class);
        instructor.setId(instructorId);
        instructor.setName("John Doe");

        Course course = new Course();
        course.setId(courseId);
        course.setName("Java Programming");

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Mockito.when(instructor.getCourses()).thenReturn(new HashSet<>()); // No courses associated with the instructor

        CourseNotAssociatedToInstructorException exception = assertThrows(
                CourseNotAssociatedToInstructorException.class,
                () -> studentService.chooseCourseForStudent(studentId, instructorId, courseId));

        assertTrue(exception.getMessage().contains("The course is not associated with the given instructor."));
    }

    @Test
    public void testChooseCourseForStudentStudentNotFound() {
        Long studentId = 1L;
        Long instructorId = 2L;
        Long courseId = 3L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                () -> studentService.chooseCourseForStudent(studentId, instructorId, courseId));

        assertTrue(exception.getMessage().contains("Student not found"));

        verify(instructorRepository, never()).findById(any());
        verify(courseRepository, never()).findById(any());
    }
}