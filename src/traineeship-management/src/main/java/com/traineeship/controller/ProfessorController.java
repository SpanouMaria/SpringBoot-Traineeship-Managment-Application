package com.traineeship.controller;

import com.traineeship.model.Evaluation;
import com.traineeship.model.EvaluationType;
import com.traineeship.model.Professor;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.EvaluationRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import com.traineeship.service.ProfessorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ProfessorServiceImpl professorService;
    @Autowired
	private EvaluationRepository evaluationRepository;
    @Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;

    
    @GetMapping("/my-positions")
    public String viewSupervisedPositions(Model model, Principal principal) {
        String username = principal.getName(); 
        List<TraineeshipPosition> supervised = professorService.getSupervisedTraineeships(username);
        model.addAttribute("supervisedPositions", supervised);
        return "professors/professor-supervised-positions";
    }
    
    @GetMapping("/evaluate/{id}")
    public String showEvaluationForm(@PathVariable Long id, Model model, Principal principal) {
        System.out.println(">> Trying to evaluate position with id=" + id);
        Optional<TraineeshipPosition> posOpt = professorService.findPositionById(id);

        if (posOpt.isEmpty()) {
            System.out.println(">> NOT FOUND!");
            return "redirect:/professors/my-positions?error=notFound";
        }

        TraineeshipPosition position = posOpt.get();
        System.out.println(">> Found position, supervisor username: " + position.getSupervisor().getUsername());
        System.out.println(">> Principal username: " + principal.getName());

        if (!position.getSupervisor().getUsername().equals(principal.getName())) {
            System.out.println(">> UNAUTHORIZED!");
            return "redirect:/professors/my-positions?error=unauthorized";
        }

        model.addAttribute("position", position);
        return "professors/professor-evaluate-traineeship";
    }



    @PostMapping("/evaluate/{id}")
    public String submitEvaluation(@PathVariable Long id,
                                   @RequestParam int motivation,
                                   @RequestParam int effectiveness,
                                   @RequestParam int efficiency,
                                   Principal principal) {

        
        TraineeshipPosition position = traineeshipPositionRepository.findById(id.intValue()).orElseThrow();

        
        Evaluation evaluation = new Evaluation();
        evaluation.setMotivation(motivation);
        evaluation.setEffectiveness(effectiveness);
        evaluation.setEfficiency(efficiency);
        evaluation.setEvaluationType(EvaluationType.PROFESSOR_REVIEW); 
        evaluation.setTraineeshipPosition(position);

        evaluationRepository.save(evaluation);

        return "redirect:/professors/my-positions?success";
    }


    
    @GetMapping("/portal-home")
    public String professorHome() {
        return "professors/portal-home";
    }


    @PostMapping("/register")
    public String registerProfessor(@ModelAttribute Professor professor, Model model) {
        Optional<Professor> existing = professorService.findById(professor.getUsername());

        if (existing.isPresent()) {
            model.addAttribute("error", "A professor with this username already exists.");

            
            professor.setUsername(null);
            model.addAttribute("professor", professor);

            return "professors/professor-form";
        }

        professorService.saveProfessor(professor);
        return "redirect:/professors/list";
    }


   
    @GetMapping("/delete")
    public String deleteProfessor(@RequestParam String username) {
        professorService.deleteById(username);
        return "redirect:/professors/list";
    }

    
    @GetMapping
    public String defaultRedirect() {
        return "redirect:/professors/list";
    }

 

    @PostMapping("/api/register")
    @ResponseBody
    public void apiRegisterProfessor(@RequestBody Professor professor) {
        professorService.saveProfessor(professor);
    }

    @GetMapping("/api/{username}")
    @ResponseBody
    public Optional<Professor> getProfessor(@PathVariable String username) {
        return professorService.findById(username);
    }

    @GetMapping("/api/{username}/traineeships")
    @ResponseBody
    public List<TraineeshipPosition> getSupervisedTraineeships(@PathVariable String username) {
        return professorService.getSupervisedTraineeships(username);
    }

    @PostMapping("/api/{username}/evaluate/{positionId}")
    @ResponseBody
    public String evaluateStudent(@PathVariable String username,
                                  @PathVariable Integer positionId,
                                  @RequestBody String evaluation) {
        return professorService.evaluateStudent(username, positionId, evaluation);
    }
}
