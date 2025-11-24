/*package com.traineeship.strategy;

import com.traineeship.model.Professor;
import com.traineeship.model.TraineeshipPosition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InterestBasedProfessorAssignment implements ProfessorAssignmentStrategy {

    @Override
    public Optional<Professor> assignProfessor(List<Professor> availableProfessors, TraineeshipPosition position) {
        return availableProfessors.stream()
                .filter(professor -> professor.getInterests().contains(position.getTopics()))
                .findFirst();
    }
}
*/