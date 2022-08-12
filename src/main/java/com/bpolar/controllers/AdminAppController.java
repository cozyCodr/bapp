package com.bpolar.controllers;

import com.bpolar.model.Search;
import com.bpolar.services.UserService;
import com.bpolar.user.Role;
import com.bpolar.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/a")
public class AdminAppController {

    private final UserService userService;

    public AdminAppController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String logIntoAdminAccount(){
        return "admin/login";
    }

    @RequestMapping("/create-account")
    public String SignupAdminAccount(Model model){
        model.addAttribute("admin", new User());
        return "admin/signup";
    }

    @PostMapping("/register")
    public String processSignUp(User user) {
        userService.saveUserWithAdminRole(user);
        return "admin/sign_up_successful";
    }

    @GetMapping("/dashboard")
    public String viewAdminDashboard(Model model) {
        model.addAttribute("patientCount", userService.findAllPatients().size());
        model.addAttribute("search", new Search());
        return "admin/admindash";
    }

    @GetMapping("/patients")
    public String viewUsersList(Model model) {
        model.addAttribute("allpatients", userService.findAllPatients());
        model.addAttribute("search", new Search());
        return "admin/patients";
    }

    @PostMapping("/find-patient")
    public String findPatient(@ModelAttribute("email") Search search, Model model){
        log.info("Patient email {}", search.getEmail());
        String email = search.getEmail();
        User patient = userService.findPatient(email);

        model.addAttribute("userDetails", patient);
        model.addAttribute("search", new Search());
        return "admin/search";
    }

    @GetMapping("/search")
    public String searchPatient(Model model) {
        model.addAttribute("search", new Search());
        return "admin/search";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        List<Role> listRoles = userService.getRoles();
        model.addAttribute("editUser", user);
        model.addAttribute("listRoles", listRoles);

        return "edit_user";
    }

    @PostMapping("/user/save")
    public String saveEditedUser(User user) {
        userService.saveEditedUser(user);
        return "redirect:/user_list";
    }
}
