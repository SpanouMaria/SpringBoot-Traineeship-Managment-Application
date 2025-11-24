package com.traineeship.service;

import com.traineeship.model.TraineeshipPosition;
import java.util.List;
import java.util.Optional;

public interface TraineeshipService {
    List<TraineeshipPosition> getAllTraineeships();
    Optional<TraineeshipPosition> findById(Integer id);
    List<TraineeshipPosition> getInProgressTraineeships();
}