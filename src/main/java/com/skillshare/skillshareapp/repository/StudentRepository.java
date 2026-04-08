package com.skillshare.skillshareapp.repository;

import com.skillshare.skillshareapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Get mentors (year >= 3)
    List<Student> findByYearGreaterThanEqual(int year);
   
    Student findByStudentId(Long studentId);
    List<Student> findByRole(String role);

    @Query("SELECT s FROM Student s WHERE s.role = :role AND (LOWER(s.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Student> findByRoleAndNameContaining(@Param("role") String role, @Param("query") String query);

}