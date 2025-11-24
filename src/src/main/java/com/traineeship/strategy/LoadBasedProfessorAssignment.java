package com.traineeship.strategy;

import com.traineeship.model.Professor;
import com.traineeship.model.TraineeshipPosition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Comparator;
import java.util.Optional;

@Component
public class LoadBasedProfessorAssignment implements ProfessorAssignmentStrategy {

    @Override
    public Optional<Professor> assignProfessor(List<Professor> availableProfessors, TraineeshipPosition position) {
        return availableProfessors.stream()
                .min(Comparator.comparingInt(professor -> professor.getSupervisedPositions().size()));
    }
}
