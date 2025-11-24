package com.traineeship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import com.traineeship.model.User;

@Controller
public class HomeController {
    
    @GetMapping("/login")
    public String login(Model model) {
    	model.addAttribute("user", new User());
        return "login";
    }
    
    @GetMapping("/")
    public String redirectAfterLogin(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_STUDENT")) {
            return "redirect:/students/portal-home";
        } else if (role.equals("ROLE_PROFESSOR")) {
            return "redirect:/professors/portal-home";
        } else if (role.equals("ROLE_COMMITTEE")) {
            return "redirect:/traineeship-committee/portal-home";
        } else if (role.equals("ROLE_COMPANY")) {
            return "redirect:/company/portal-home";
        } else {
            return "redirect:/access-denied";
        }
    }

}