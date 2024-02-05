package com.isc.assessment.repository;

import com.isc.assessment.model.Course;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course , Long> {
    Optional<Course> findByCourseNumber(Long courseNumber);

}
