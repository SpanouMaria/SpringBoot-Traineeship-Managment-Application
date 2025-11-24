package com.traineeship.repository;

import com.traineeship.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    /**
     * Retrieves evaluations for a specific traineeship position.
     */
    List<Evaluation> findByTraineeshipPositionId(Integer positionId);
}
