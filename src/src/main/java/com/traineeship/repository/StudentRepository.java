package com.traineeship.repository;

import com.traineeship.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.traineeship.model.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByUsername(String username);
}

