package com.traineeship.service;

import com.traineeship.model.Professor;
import com.traineeship.model.TraineeshipPosition;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {

    void saveProfessor(Professor professor);

    void deleteById(String username);

    Optional<Professor> findById(String username);

    List<Professor> findAll();

    List<TraineeshipPosition> getSupervisedTraineeships(String username);

    Optional<TraineeshipPosition> findPositionById(Long id);

    String evaluateStudent(String username, Integer positionId, String evaluation);
}