package com.traineeship.service;

import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.TraineeshipPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeshipPositionServiceImpl implements TraineeshipPositionService {

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Override
    public List<TraineeshipPosition> findByStatus(String status) {
        return traineeshipPositionRepository.findByStatus(status);
    }
    
}