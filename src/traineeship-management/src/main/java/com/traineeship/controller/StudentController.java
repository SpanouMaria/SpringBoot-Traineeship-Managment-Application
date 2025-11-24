package com.traineeship.controller;

import com.traineeship.model.Application;
import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.ApplicationRepository;
import com.traineeship.repository.StudentRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import com.traineeship.service.ApplicationService;
import com.traineeship.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Autowired
    private ApplicationService applicationService;

   
    @GetMapping("/portal-home")
    public String portalHome(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        if (studentOpt.isPresent()) {
            model.addAttribute("students", studentOpt.get());
        }
        return "students/portal-home";
    }

    
    @GetMapping("/available-positions")
    public String availablePositions(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Student> studentOpt = studentRepository.findByUsername(username);

        if (studentOpt.isEmpty()) {
            return "redirect:/login";
        }
        Student student = studentOpt.get();

        List<TraineeshipPosition> positions = traineeshipPositionRepository.findAll();
        Map<Long, Boolean> hasApplied = new HashMap<>();
        for (TraineeshipPosition pos : positions) {
            hasApplied.put(pos.getId(), applicationService.hasApplied(student, pos));
        }
        model.addAttribute("hasApplied", hasApplied);


        model.addAttribute("positions", positions);
        
       

        return "students/available-positions";
    }


   
    @PostMapping("/apply/{positionId}")
    public String applyForPosition(@PathVariable Integer positionId, Principal principal) {
        String username = principal.getName();
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        Optional<TraineeshipPosition> posOpt = traineeshipPositionRepository.findById(positionId);

        if (studentOpt.isPresent() && posOpt.isPresent()) {
            applicationService.apply(studentOpt.get(), posOpt.get());
        }
        return "redirect:/students/available-positions";
    }


    
    @GetMapping("/applications")
    public String viewApplications(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Student> studentOpt = studentRepository.findByUsername(username);

        if (studentOpt.isEmpty()) {
            return "redirect:/login";
        }
        Student student = studentOpt.get();
        List<Application> applications = applicationService.getApplicationsByStudent(student);

        model.addAttribute("applications", applications);
        return "students/applications";
    }

    @PostMapping("/cancel/{applicationId}")
    public String cancelApplication(@PathVariable Long applicationId, Principal principal) {
        
        applicationService.cancelApplication(applicationId);
        return "redirect:/students/applications";
    }
}
