package com.traineeship.service;

import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.TraineeshipPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeshipServiceImpl implements TraineeshipService {

    @Autowired
    private TraineeshipPositionRepository traineeshipRepository;

    @Override
    public List<TraineeshipPosition> getAllTraineeships() {
        return traineeshipRepository.findAll();
    }

    @Override
    public Optional<TraineeshipPosition> findById(Integer id) {
        return traineeshipRepository.findById(id);
    }

    @Override
    public List<TraineeshipPosition> getInProgressTraineeships() {
        return traineeshipRepository.findInProgressWithRelations("IN_PROGRESS");
    }

}
