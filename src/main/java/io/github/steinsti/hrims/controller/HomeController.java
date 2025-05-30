package io.github.steinsti.hrims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
	String home(Model model){
		boolean isAuthenticated = false;
		model.addAttribute("isAuthenticated", isAuthenticated);
		return "index";
	}
    
}
