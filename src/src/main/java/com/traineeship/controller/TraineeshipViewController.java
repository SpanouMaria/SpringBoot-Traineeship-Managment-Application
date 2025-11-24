package com.traineeship.controller;

import com.traineeship.model.TraineeshipPosition;
import com.traineeship.service.TraineeshipCommitteeService;
import com.traineeship.service.TraineeshipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/traineeship-committee")
public class TraineeshipViewController {

    @Autowired
    private TraineeshipCommitteeService committeeService;

    @Autowired
    private TraineeshipServiceImpl traineeshipService;

    @GetMapping("/view-applicants")
    public String viewApplicants(Model model) {
        model.addAttribute("students", committeeService.getAppliedStudents());
        return "traineeship/view-applicants";
    }

    @GetMapping("/in-progress-traineeships")
    public String viewInProgressTraineeships(Model model) {
        List<TraineeshipPosition> inProgress = traineeshipService.getInProgressTraineeships();
        model.addAttribute("traineeships", inProgress);
        return "traineeship/in-progress-traineeships";
    }
    /**
     * Finalizes a traineeship by marking it as pass/fail.
     */
    @GetMapping("/finalize-traineeship/{traineeshipId}/{passFail}")
    public String finalizeTraineeship(@PathVariable Integer traineeshipId,
                                      @PathVariable boolean passFail,
                                      Model model) {
        committeeService.finalizeTraineeship(traineeshipId, passFail);
        model.addAttribute("traineeshipId", traineeshipId);
        model.addAttribute("passed", passFail);
        return "traineeship/finalize-confirmation";
    }
}
