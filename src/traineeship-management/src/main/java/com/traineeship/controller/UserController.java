package com.traineeship.controller;

import com.traineeship.model.*;
import com.traineeship.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Role role = user.getRole();

        switch (role) {
            case STUDENT -> {
                Student s = new Student();
                s.setUsername(user.getUsername());
                user.getPassword();
                s.setStudentName(user.getName());
                studentRepository.save(s);
            }
            case PROFESSOR -> {
                Professor p = new Professor();
                p.setUsername(user.getUsername());
                user.getPassword();
                p.setProfessorName(user.getName());
                professorRepository.save(p);
            }
            case COMPANY -> {
                Company c = new Company();
                c.setUsername(user.getUsername());
                user.getPassword();
                c.setCompanyName(user.getName());
                companyRepository.save(c);
            }
            case COMMITTEE-> {
                
                System.out.println("Committee member registered: " + user.getUsername());
            }
            
            
        }

        return "redirect:/login";
    }

    @GetMapping("/{username}")
    @ResponseBody
    public User getUser(@PathVariable String username) {
        return userRepository.findById(username).orElse(null);
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User()); 
        return "login";
    }


}
