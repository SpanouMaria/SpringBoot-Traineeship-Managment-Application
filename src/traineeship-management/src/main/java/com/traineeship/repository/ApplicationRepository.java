package com.traineeship.repository;

import com.traineeship.model.Application;
import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(Student student);
    Optional<Application> findByStudentAndPosition(Student student, TraineeshipPosition position);
    List<Application> findByPosition(TraineeshipPosition position);
    boolean existsByStudentUsernameAndPositionId(String studentUsername, Long positionId);

}
