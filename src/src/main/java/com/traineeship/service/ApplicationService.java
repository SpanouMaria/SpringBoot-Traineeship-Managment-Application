package com.traineeship.service;

import com.traineeship.model.Application;
import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    
   


    public List<Application> getApplicationsByStudent(Student student) {
        return applicationRepository.findByStudent(student);
    }

    public boolean hasApplied(Student student, TraineeshipPosition position) {
        return applicationRepository.findByStudentAndPosition(student, position).isPresent();
    }

    public Application apply(Student student, TraineeshipPosition position) {
        if (hasApplied(student, position)) return null;
        Application app = new Application(null, student, position, "PENDING");
        return applicationRepository.save(app);
    }

    public void cancelApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
    
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
