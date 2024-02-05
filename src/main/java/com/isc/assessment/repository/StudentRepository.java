package com.isc.assessment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isc.assessment.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentNumber(Long studentNumber);
    // Optional<Student> findById(Integer id);
    // List<Student> findAll();
    // void delete(Integer id);

}
