package com.traineeship.service;

import com.traineeship.model.Evaluation;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.EvaluationRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    /**
     * Submits a new evaluation.
     */
    public String submitEvaluation(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
        return "Evaluation submitted successfully.";
    }

    /**
     * Retrieves evaluations by traineeship position ID.
     */
    public List<Evaluation> getEvaluationsByTraineeship(Integer positionId) {
        return evaluationRepository.findByTraineeshipPositionId(positionId);
    }

    /**
     * Finds an evaluation by its ID.
     */
    public Optional<Evaluation> findById(Integer evaluationId) {
        return evaluationRepository.findById(evaluationId);
    }
}