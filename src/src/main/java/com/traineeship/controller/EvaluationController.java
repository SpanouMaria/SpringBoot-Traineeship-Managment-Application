package com.traineeship.controller;

import com.traineeship.model.Evaluation;
import com.traineeship.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    /**
     * Endpoint to submit a new evaluation.
     */
    @PostMapping("/submit")
    public String submitEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationService.submitEvaluation(evaluation);
    }

    /**
     * Endpoint to retrieve all evaluations for a traineeship position.
     */
    @GetMapping("/traineeship/{positionId}")
    public List<Evaluation> getEvaluationsByTraineeship(@PathVariable Integer positionId) {
        return evaluationService.getEvaluationsByTraineeship(positionId);
    }

    /**
     * Endpoint to get a specific evaluation by ID.
     */
    @GetMapping("/{evaluationId}")
    public Optional<Evaluation> getEvaluation(@PathVariable Integer evaluationId) {
        return evaluationService.findById(evaluationId);
    }
}

