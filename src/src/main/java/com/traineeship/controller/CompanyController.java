package com.traineeship.controller;

import com.traineeship.model.Company;
import com.traineeship.model.Evaluation;
import com.traineeship.model.EvaluationType;
import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.CompanyRepository;
import com.traineeship.repository.EvaluationRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import com.traineeship.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

	private Company company;

	@Autowired
	private EvaluationRepository evaluationRepository;
    
    @PostMapping("/register")
    public void registerCompany(@RequestBody Company company) {
        companyService.saveCompany(company);
    }

    @GetMapping("/{username}")
    public Optional<Company> getCompany(@PathVariable String username) {
        return companyService.findById(username);
    }

    
    @GetMapping("/{username}/positions")
    public List<TraineeshipPosition> getCompanyPositions(@PathVariable String username) {
        return companyService.getCompanyPositions(username);
    }

    
    @DeleteMapping("/{username}/delete-position/{positionId}")
    public String deleteTraineeshipPosition(@PathVariable String username, @PathVariable Integer positionId) {
        return companyService.deleteTraineeshipPosition(username, positionId);
    }

 
    @PostMapping("/{username}/evaluate/{positionId}")
    public String evaluateStudent(@PathVariable String username, @PathVariable Integer positionId, @RequestBody String evaluation) {
        return companyService.evaluateStudent(username, positionId, evaluation);
    }
    
    @GetMapping("/portal-home")
    public String companyHome(Principal principal, Model model) {
        String username = principal.getName();
		Company company = companyRepository.findById(username).orElseThrow();
        model.addAttribute("company", company);
        return "company/portal-home";
    }
    
    @GetMapping("/positions")
    public String viewPositions(Model model, Principal principal) {
        String username = principal.getName();
        List<TraineeshipPosition> positions = companyService.getCompanyPositions(username); // ή όπως το λες
        model.addAttribute("positions", positions);
        return "company/company-positions";
    }

    @GetMapping("/add-position")
    public String showAddPositionForm(Model model) {
        model.addAttribute("position", new TraineeshipPosition());
        return "company/company-add-position";
    }

    @PostMapping("/add-position")
    public String addPosition(@ModelAttribute("position") TraineeshipPosition position, Principal principal) {
        String username = principal.getName();
        Company company = companyRepository.findById(username).orElseThrow();

        if (position.getStatus() == null || position.getStatus().isBlank()) {
            position.setStatus("OPEN");
        }
        
        position.setCompany(company);

        traineeshipPositionRepository.save(position);

        return "redirect:/company/positions";
    }


    @GetMapping("/assigned")
    public String viewAssigned(Model model, Principal principal) {
        String username = principal.getName();
        List<TraineeshipPosition> assigned = traineeshipPositionRepository
            .findByCompany_UsernameAndAssignedStudentIsNotNull(username);
        model.addAttribute("assignments", assigned);
        return "company/company-assigned";
    }


    @GetMapping("/evaluate/{id}")
    public String showCompanyEvaluationForm(@PathVariable Long id, Model model, Principal principal) {
        TraineeshipPosition position = traineeshipPositionRepository.findById(id.intValue()).orElseThrow();
        model.addAttribute("position", position);
        return "company/evaluate";
    }

    @PostMapping("/evaluate/{id}")
    public String submitEvaluation(@PathVariable Long id,
                                   @RequestParam int motivation,
                                   @RequestParam int skills,
                                   @RequestParam int cooperation,
                                   Principal principal) {

    	 TraineeshipPosition position = traineeshipPositionRepository.findById(id.intValue()).orElseThrow();

         
         Evaluation evaluation = new Evaluation();
         evaluation.setMotivation(motivation);
         evaluation.setSkills(skills);
         evaluation.setCooperation(cooperation);
         evaluation.setEvaluationType(EvaluationType.COMPANY_REVIEW); 
         evaluation.setTraineeshipPosition(position);

         evaluationRepository.save(evaluation);
        

      
        return "redirect:/company/assigned?success";
    }
}