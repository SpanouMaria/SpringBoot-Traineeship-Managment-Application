package com.traineeship.controller;

import com.traineeship.model.TraineeshipPosition;
import com.traineeship.service.TraineeshipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/traineeship-committee")
public class TraineeshipController {
	@Autowired
	private TraineeshipServiceImpl traineeshipService;


    @GetMapping
    public List<TraineeshipPosition> getAllTraineeships() {
        return traineeshipService.getAllTraineeships();
    }

    @GetMapping("/{id}")
    public Optional<TraineeshipPosition> getTraineeship(@PathVariable Integer id) {
        return traineeshipService.findById(id);
    }
    
    @GetMapping("/portal")
    public String committeePortal() {
        return "traineeship-committee/portal-home";
    }
}
