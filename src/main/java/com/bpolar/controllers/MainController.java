package com.bpolar.controllers;

import com.bpolar.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	public final UserService userService;

	public MainController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String viewHomepage(){
		return "home/index";
	}

	@GetMapping("/treatment")
	public String viewTreatment() {
		return "home/treatment";
	}

	@GetMapping("/readmore")
	public String viewAbout() {
		return "home/about";
	}

	@GetMapping("/diagnosis")
	public String startDiagnosis(Model model){
		return "user/signup";
	}

}
