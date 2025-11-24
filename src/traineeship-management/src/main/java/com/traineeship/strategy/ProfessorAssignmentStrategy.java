package com.traineeship.strategy;

import com.traineeship.model.Professor;
import com.traineeship.model.TraineeshipPosition;

import java.util.List;
import java.util.Optional;

public interface ProfessorAssignmentStrategy {
    Optional<Professor> assignProfessor(List<Professor> availableProfessors, TraineeshipPosition position);
}
