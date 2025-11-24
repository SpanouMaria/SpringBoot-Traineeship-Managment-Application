package com.traineeship.service;

import com.traineeship.strategy.ProfessorAssignmentStrategy;


import com.traineeship.model.Professor;
import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.ProfessorRepository;
import com.traineeship.repository.StudentRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TraineeshipCommitteeService {
	
	@Autowired
    private ProfessorAssignmentStrategy professorAssignmentStrategy; 
    @Autowired
    private TraineeshipPositionRepository traineeshipRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ProfessorRepository professorRepository;

    /**
     * Retrieves a list of students who applied for a traineeship.
     */
    public List<Student> getAppliedStudents() {
        return studentRepository.findAll().stream()
                .filter(Student::isLookingForTraineeship)
                .collect(Collectors.toList());
    }

    /**
     * Searches for available traineeships based on a student's interests and location.
     */
    public List<TraineeshipPosition> searchTraineeshipsForStudent(String username) {
        Optional<Student> studentOpt = studentRepository.findById(username);

        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        return searchTraineeshipsForStudent(studentOpt.get());
    }

    /**
     * Helper method to search traineeships for a student object.
     */
    private List<TraineeshipPosition> searchTraineeshipsForStudent(Student student) {
        return traineeshipRepository.findAll().stream()
                .filter(position -> !position.isAssigned() &&
                        (position.getTopics().contains(student.getInterests()) ||
                                position.getCompany().getCompanyLocation().equals(student.getPreferredLocation())))
                .collect(Collectors.toList());
    }

    /**
     * Assigns a student to a traineeship position.
     */
    public String assignTraineeship(String studentUsername, Integer traineeshipId) {
        Optional<Student> studentOpt = studentRepository.findById(studentUsername);
        Optional<TraineeshipPosition> positionOpt = traineeshipRepository.findById(traineeshipId);

        if (studentOpt.isPresent() && positionOpt.isPresent() && !positionOpt.get().isAssigned()) {
            TraineeshipPosition position = positionOpt.get();
            position.setStudent(studentOpt.get());
            position.setAssigned(true);
            traineeshipRepository.save(position);
            return "Traineeship assigned successfully.";
        }
        return "Failed to assign traineeship: Either student or position not found, or position is already assigned.";
    }

    /**
     * Assigns a professor to a traineeship position using strategy.
     */
    public String assignProfessor(Integer traineeshipId) {
        Optional<TraineeshipPosition> positionOpt = traineeshipRepository.findById(traineeshipId);
        List<Professor> availableProfessors = professorRepository.findAll();

        if (positionOpt.isPresent() && !availableProfessors.isEmpty()) {
            TraineeshipPosition position = positionOpt.get();
            Optional<Professor> assignedProfessor = professorAssignmentStrategy.assignProfessor(availableProfessors, position);

            if (assignedProfessor.isPresent()) {
                position.setSupervisor(assignedProfessor.get());
                traineeshipRepository.save(position);
                return "Professor assigned successfully based on strategy.";
            }
        }
        return "Failed to assign professor: No suitable professor found.";
    }

    /**
     * Monitors in-progress traineeships.
     */
    public List<TraineeshipPosition> getInProgressTraineeships() {
        return traineeshipRepository.findAll().stream()
                .filter(TraineeshipPosition::isAssigned)
                .collect(Collectors.toList());
    }
    
    /**
     * Completes a traineeship by marking it as passed or failed.
     */
    public String finalizeTraineeship(Integer traineeshipId, boolean passFail) {
        Optional<TraineeshipPosition> positionOpt = traineeshipRepository.findById(traineeshipId);

        if (positionOpt.isPresent()) {
            TraineeshipPosition position = positionOpt.get();
            position.setPassFailGrade(passFail);
            traineeshipRepository.save(position);
            return "Traineeship finalized successfully.";
        }
        return "Traineeship not found.";
    }

    public Optional<Student> getStudentByUsername(String username) {
        return studentRepository.findById(username);
    }   
    

}
