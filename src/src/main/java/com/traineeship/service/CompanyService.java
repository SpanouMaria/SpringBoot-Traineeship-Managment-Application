package com.traineeship.service;

import com.traineeship.model.Company;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.CompanyRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public Optional<Company> findById(String username) {
        return companyRepository.findById(username);
    }

    public String createTraineeshipPosition(String username, TraineeshipPosition position) {
        Optional<Company> companyOpt = companyRepository.findById(username);
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            position.setCompany(company);
            traineeshipPositionRepository.save(position);
            return "Traineeship position created successfully.";
        }
        return "Company not found.";
    }
    
    public List<TraineeshipPosition> getCompanyPositions(String username) {
        return traineeshipPositionRepository.findByCompanyUsername(username);
    }


    public String deleteTraineeshipPosition(String username, Integer positionId) {
        Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
        if (positionOpt.isPresent() && positionOpt.get().getCompany().getUsername().equals(username)) {
            traineeshipPositionRepository.delete(positionOpt.get());
            return "Traineeship position deleted successfully.";
        }
        return "Position not found or unauthorized access.";
    }

    public String evaluateStudent(String username, Integer positionId, String evaluation) {
        Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
        if (positionOpt.isPresent() && positionOpt.get().getCompany().getUsername().equals(username)) {
            TraineeshipPosition position = positionOpt.get();
            position.setStudentLogbook(position.getStudentLogbook() + "\nCompany Evaluation: " + evaluation);
            traineeshipPositionRepository.save(position);
            return "Student evaluated successfully.";
        }
        return "Invalid request: Either position not found or unauthorized access.";
    }
}