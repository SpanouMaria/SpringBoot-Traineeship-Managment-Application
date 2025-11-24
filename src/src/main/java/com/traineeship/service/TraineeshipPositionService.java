package com.traineeship.service;

import com.traineeship.model.TraineeshipPosition;
import java.util.List;

public interface TraineeshipPositionService {
    List<TraineeshipPosition> findByStatus(String status);
}
