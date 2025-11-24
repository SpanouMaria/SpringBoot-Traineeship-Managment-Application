package com.traineeship.service;

import com.traineeship.model.Professor;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.ProfessorRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;


    @Override
    public void saveProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    @Override
    public void deleteById(String username) {
        professorRepository.deleteById(username);
    }

    @Override
    public Optional<Professor> findById(String username) {
        return professorRepository.findById(username);
    }

    @Override
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    @Override
    public Optional<TraineeshipPosition> findPositionById(Long id) {
        return traineeshipPositionRepository.findById(id.intValue());
    }

    @Override
    public List<TraineeshipPosition> getSupervisedTraineeships(String username) {
        return traineeshipPositionRepository.findAll().stream()
                .filter(position -> position.getSupervisor() != null)
                .filter(position -> position.getSupervisor().getUsername().equals(username))
                .toList();
    }


    @Override
    public String evaluateStudent(String username, Integer positionId, String evaluation) {
        Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId.intValue());
        if (positionOpt.isPresent() && positionOpt.get().getSupervisor().getUsername().equals(username)) {
            TraineeshipPosition position = positionOpt.get();
            position.setStudentLogbook(position.getStudentLogbook() + "\nProfessor Evaluation: " + evaluation);
            traineeshipPositionRepository.save(position);
            return "Student evaluated successfully.";
        }
        return "Invalid request: Either position not found or unauthorized access.";
    }
}