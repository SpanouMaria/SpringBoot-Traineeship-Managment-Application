package com.traineeship.service;

import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.StudentRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public Optional<Student> retrieveProfile(String username) {
        return studentRepository.findById(username);
    }

    public String applyForTraineeship(String username, Integer positionId) {
        Optional<Student> studentOpt = studentRepository.findById(username);
        Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);

        if (studentOpt.isPresent() && positionOpt.isPresent()) {
            Student student = studentOpt.get();
            TraineeshipPosition position = positionOpt.get();
            
            if (!position.isAssigned()) {
                position.setStudent(student);
                position.setAssigned(true);
                traineeshipPositionRepository.save(position);
                return "Successfully applied for traineeship.";
            } else {
                return "Traineeship position already assigned.";
            }
        }
        return "Student or position not found.";
    }

    public String fillLogbook(String username, Integer positionId, String logbookEntry) {
        Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);

        if (positionOpt.isPresent() && positionOpt.get().getStudent().getUsername().equals(username)) {
            TraineeshipPosition position = positionOpt.get();
            position.setStudentLogbook(logbookEntry);
            traineeshipPositionRepository.save(position);
            return "Logbook updated successfully.";
        }
        return "Invalid request: Either position not found or student not assigned to this traineeship.";
    }

    public List<TraineeshipPosition> getAvailableTraineeships() {
        return traineeshipPositionRepository.findByIsAssignedFalse();
    }
}
