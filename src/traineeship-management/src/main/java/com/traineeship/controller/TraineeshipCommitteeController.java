package com.traineeship.controller;

import com.traineeship.model.Application;
import com.traineeship.model.Evaluation;
import com.traineeship.model.Professor;
import com.traineeship.model.Role;
import com.traineeship.model.Student;
import com.traineeship.model.StudentForm;

import com.traineeship.model.TraineeshipPosition;
import com.traineeship.model.User;
import com.traineeship.repository.ApplicationRepository;
import com.traineeship.repository.ProfessorRepository;
import com.traineeship.repository.StudentRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import com.traineeship.repository.UserRepository;
import com.traineeship.service.ApplicationService;
import com.traineeship.service.TraineeshipCommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.traineeship.service.TraineeshipPositionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/traineeship-committee")
public class TraineeshipCommitteeController {
	
	
	@Autowired
	private ProfessorRepository professorRepository;

    @Autowired
    private TraineeshipCommitteeService committeeService;

    @Autowired private UserRepository userRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Autowired private ApplicationService applicationService;
    
    @Autowired
    private ApplicationRepository applicationRepository;


    @GetMapping("/add-student")
    public String showStudentForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "add-student";
    }

    @PostMapping("/add-student")
    public String registerStudent(@ModelAttribute("studentForm") StudentForm form) {
        // Create User
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        // Create Student
        Student student = new Student();
        student.setUsername(form.getUsername());
        student.setStudentName(form.getStudentName());
        student.setAM(form.getAm());
        student.setInterests(form.getInterests());
        student.setSkills(form.getSkills());
        student.setPreferredLocation(form.getPreferredLocation());
        studentRepository.save(student);

        return "redirect:/traineeship-committee/portal-home";
    }

    /**
     * Retrieves all students who applied for a traineeship.
     */
    @GetMapping("/applied-students")
    public List<Student> getAppliedStudents() {
        return committeeService.getAppliedStudents();
    }

    /**
     * Searches for available traineeships based on a student's interests and location.
     */
    @GetMapping("/search-traineeships/{username}")
    public List<TraineeshipPosition> searchTraineeships(@PathVariable String username) {
        return committeeService.searchTraineeshipsForStudent(username);
    }

    /**
     * Assigns a student to a traineeship.
     */
    @PostMapping("/assign-student/{studentUsername}/{traineeshipId}")
    public String assignTraineeship(@PathVariable String studentUsername, @PathVariable Integer traineeshipId) {
        return committeeService.assignTraineeship(studentUsername, traineeshipId);
    }

    /**
     * Assigns a professor to a traineeship.
     */
    @PostMapping("/assign-professor/{traineeshipId}")
    public String assignProfessor(@PathVariable Integer traineeshipId) {
        return committeeService.assignProfessor(traineeshipId);
    }
    
 
    @GetMapping("/view-applications")
    public String viewAllApplications(Model model) {
        List<Application> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "traineeship-committee/view-applications";
    }

    @GetMapping("/view-application/{id}")
    public String viewApplicationDetails(@PathVariable Long id, Model model) {
        Application app = applicationRepository.findById(id).orElseThrow();
        model.addAttribute("app", app);
        return "traineeship-committee/view-application-details";
    }


    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @GetMapping("/assign-positions")
    public String assignPositions(Model model) {
        List<TraineeshipPosition> positions = traineeshipPositionRepository.findAll();
        model.addAttribute("positions", positions);
        return "traineeship-committee/assign-positions";
    }
    
    @GetMapping("/assign-position/{id}")
    public String showAssignStudentForm(@PathVariable("id") Integer positionId, Model model) {
        TraineeshipPosition position = traineeshipPositionRepository.findById(positionId).orElse(null);
        List<Student> students = studentRepository.findAll();
        model.addAttribute("position", position);
        model.addAttribute("students", students);
        return "traineeship-committee/assign-student";
    }

    @PostMapping("/assign-position")
    public String assignStudentToPosition(
            @RequestParam("positionId") Integer positionId,
            @RequestParam("studentId") String studentId) {

        TraineeshipPosition position = traineeshipPositionRepository.findById(positionId).orElse(null);
        Student student = studentRepository.findById(studentId).orElse(null);

        if (position != null && student != null) {
       
            position.setAssignedStudent(student);
            position.setStatus("ASSIGNED");
            traineeshipPositionRepository.save(position);

            
            List<Application> applicationsForPosition = applicationRepository.findByPosition(position);

            for (Application app : applicationsForPosition) {
                if (app.getStudent().getUsername().equals(studentId)) {
                    app.setStatus("APPROVED"); 
                } else {
                    app.setStatus("REJECTED");
                }
                applicationRepository.save(app);
            }
        }
        return "redirect:/traineeship-committee/assign-positions";
    }

    @GetMapping("/assign-supervisors")
    public String viewAssignSupervisors(Model model) {
        
        List<TraineeshipPosition> positions = traineeshipPositionRepository.findByStatus("ASSIGNED");
        model.addAttribute("positions", positions);
        return "traineeship-committee/assign-supervisors";
    }

    @GetMapping("/assign-supervisor/{id}")
    public String showAssignSupervisorForm(@PathVariable("id") Integer positionId, Model model) {
        TraineeshipPosition position = traineeshipPositionRepository.findById(positionId).orElse(null);
        List<Professor> professors = professorRepository.findAll();
        model.addAttribute("position", position);
        model.addAttribute("professors", professors);
        return "traineeship-committee/assign-supervisor";
    }


    @PostMapping("/assign-supervisor")
    public String assignSupervisor(
            @RequestParam("positionId") Integer positionId,
            @RequestParam("professorId") String professorId) {
        TraineeshipPosition position = traineeshipPositionRepository.findById(positionId).orElse(null);
        Professor professor = professorRepository.findById(professorId).orElse(null);
        if (position != null && professor != null) {
            position.setSupervisor(professor);
            traineeshipPositionRepository.save(position);
        }
    
        return "redirect:/traineeship-committee/assign-supervisors";
    }



    @GetMapping("/in-progress")
    public String inProgress(Model model) {
        List<TraineeshipPosition> inProgress = traineeshipPositionRepository.findByStatusIn(List.of("OPEN", "ASSIGNED"));
        model.addAttribute("inProgress", inProgress);
        return "traineeship-committee/in-progress";
    }



    
    @GetMapping("/evaluate-final")
    public String evaluateFinal(Model model) {
        List<TraineeshipPosition> toEvaluate = traineeshipPositionRepository.findByStatus("ASSIGNED");
        model.addAttribute("toEvaluate", toEvaluate);
        return "traineeship-committee/evaluate-final";
    }

    @GetMapping("/evaluate-final/{id}")
    public String showFinalizeEvaluationForm(@PathVariable Long id, Model model) {
        TraineeshipPosition position = traineeshipPositionRepository.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Invalid position id:" + id));

        List<Evaluation> evaluations = position.getEvaluations();

        Evaluation professorEvalObj = evaluations.stream()
            .filter(e -> "PROFESSOR_REVIEW".equalsIgnoreCase(e.getEvaluationType().toString()))
            .findFirst().orElse(null);

        Evaluation companyEvalObj = evaluations.stream()
            .filter(e -> "COMPANY_REVIEW".equalsIgnoreCase(e.getEvaluationType().toString()))
            .findFirst().orElse(null);

        model.addAttribute("position", position);
        model.addAttribute("professorEval", professorEvalObj);
        model.addAttribute("companyEval", companyEvalObj);
        return "traineeship-committee/evaluate-final-form";
    }


    
    @PostMapping("/evaluate-final/{id}")
    public String finalizeEvaluation(
            @PathVariable Long id,
            @RequestParam("passFailGrade") String grade) {

        TraineeshipPosition position = traineeshipPositionRepository.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Invalid position id:" + id));

       
        if ("PASS".equalsIgnoreCase(grade)) {
            position.setPassFailGrade(true);
            position.setStatus("COMPLETED");
        } else {
            position.setPassFailGrade(false);
            position.setStatus("FAILED");
        }


        traineeshipPositionRepository.save(position);

        return "redirect:/traineeship-committee/evaluate-final?success";
    }

  
    @GetMapping("/evaluations")
    public String viewEvaluations(Model model) {
        List<TraineeshipPosition> evaluatedPositions = traineeshipPositionRepository.findWithAnyEvaluation();
        model.addAttribute("evaluatedPositions", evaluatedPositions);
        return "traineeship-committee/evaluations";
    }
    
    @GetMapping("/portal-home")
    public String committeeHome() {
        return "traineeship-committee/portal-home";
    }


}
