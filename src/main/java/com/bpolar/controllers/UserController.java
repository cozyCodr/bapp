package com.bpolar.controllers;

import com.bpolar.repository.UserRepository;
import com.bpolar.services.UserService;
import com.bpolar.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/u")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/create-account")
    public String createUserAccount(Model model){
        model.addAttribute("user", new User());
        return "user/signup";
    }

    @GetMapping("/nowhome")
    public String nowHome(){
        return "user/nowhome";
    }

    @GetMapping("/login")
    public String logIntoAccount(){
        return "user/login";
    }

    @PostMapping("/register")
    public String processSignUp(User user) {
        userService.saveUserWithDefaultRole(user);
        return "sign_up_successful";
    }

    @GetMapping("/dashboard")
    public String userDashboard(){
        return "user/nowuserdash";
    }

    @GetMapping("/profile")
    public String userProfile(Model model,
                              @CurrentSecurityContext(expression="authentication?.name") String username){
        model.addAttribute("user", userService.findPatient(username));
        return "user/profile";
    }

    @GetMapping("/moodscale")
    public String getMoodScaleTest(Model model,
                                   @CurrentSecurityContext(expression="authentication?.name") String username){
        model.addAttribute("user", userService.findPatient(username));
        return "user/moodscale";
    }

    @GetMapping("/setmood/{m}")
    public String setPatientMood(@PathVariable int m,
                                 @CurrentSecurityContext(expression="authentication?.name") String username){
        User user = userService.findPatient(username);
        switch(m){
            case 0:
            case 1:
                user.setMood("Severe Depression");
                userRepository.save(user);
                break;
            case 2:
            case 3:
                user.setMood("Mild to Moderate Depression");
                userRepository.save(user);
                break;
            case 4:
            case 5:
            case 6:
                user.setMood("Balanced Mood");
                userRepository.save(user);
                break;
            case 7:
            case 8:
                user.setMood("Hypomania");
                userRepository.save(user);
                break;
            case 9:
            case 10:
                user.setMood("Mania");
                userRepository.save(user);
                break;
        }
        return "redirect:/u/profile";
    }

    @GetMapping("/editnames")
    public String editNames(Model model,
                            @CurrentSecurityContext(expression="authentication?.name") String username){
        model.addAttribute("user", userService.findPatient(username));
        return "user/editnames";
    }

    @PostMapping("/save-name")
    public String saveEditedName(Model model,
                                 @CurrentSecurityContext(expression="authentication?.name") String username,
                                 @ModelAttribute("user")User user){
        userService.saveUser(user);
        return "user/profile";
    }

    @GetMapping("/editage")
    public String editAge(Model model,
                          @CurrentSecurityContext(expression="authentication?.name") String username){
        model.addAttribute("user", userService.findPatient(username));
        log.info("Patient name {}", userService.findPatient(username).getFullName());
        return "user/editage";
    }

    @PostMapping("/save-age")
    public String saveEditedAge(Model model,
                                 @CurrentSecurityContext(expression="authentication?.name") String username,
                                 @ModelAttribute("user")User user){
        userService.saveUser(user);
        return "user/profile";
    }

    @GetMapping("/step-one")
    public String stepOne(){
        return "user/nowstepone";
    }

    @GetMapping("/step-two/{num}")
    public String stepTwo(@PathVariable int num){
        if(num == 1){
            return "user/nowsteptwobipolar";
        }
        return "user/nowsteptwoother";
    }

    @GetMapping("/step-three/{c}/{num}")
    public String stepThree(@PathVariable char c,
                            @PathVariable int num,
                            Model model,
                            @CurrentSecurityContext(expression="authentication?.name") String username){

        User user = userRepository.findByEmail((String)username);

        if (c == 'o' || c == 'b') {

            if (c == 'b') {
                if (num == 1) {
                    return "user/nowstepthreebipolar";
                }
                if (num == 2001) {
                    // todo: add diagnosis to User
                    user.setDiagnoses("Cyclothemia");
                    userRepository.save(user);
                    model.addAttribute("diagnosis", "Cyclothemia");
                    return "user/nowfinalstep";
                }
            }
            if (c == 'o') {
                if (num == 1) {
                    return "user/nowstepthreeother";
                }
                if (num == 2002) {
                    // todo: add diagnosis to User
                    user.setDiagnoses("ADHD");
                    userRepository.save(user);
                    model.addAttribute("diagnosis", "Attention Deficit Hyperactivity Disorder (ADHD)");
                    return "user/nowfinalstep";
                }
            }
        }
        return null;
    }

    @GetMapping("/final-step/{c}/{num}")
    public String finalStep(@PathVariable char c,
                            @PathVariable int num,
                            Model model,
                            @CurrentSecurityContext(expression="authentication?.name") String username){

        User user = userRepository.findByEmail((String)username);

        if (c == 'o' || c == 'b') {
            if (c == 'o') {
                switch (num) {
                    case 1:
                        model.addAttribute("diagnosis", "Major Depressive Disorder (MDD)");
                        // todo: add diagnosis to User
                        user.setDiagnoses("Major Depressive Disorder");
                        userRepository.save(user);
                        return "user/nowfinalstep";
                    case 2:
                        model.addAttribute("diagnosis", "Borderline Personality Disorder");
                        // todo: add diagnosis to User
                        user.setDiagnoses("Borderline Personality Disorder");
                        userRepository.save(user);
                        return "user/nowfinalstep";
                }
            }
            if (c == 'b') {
                switch (num) {
                    case 1:
                        model.addAttribute("diagnosis", "Bipolar I");
                        // todo: add diagnosis to User
                        user.setDiagnoses("Bipolar I");
                        userRepository.save(user);
                        return "user/nowfinalstep";
                    case 2:
                        model.addAttribute("diagnosis", "Bipolar II");
                        // todo: add diagnosis to User
                        user.setDiagnoses("Bipolar II");
                        userRepository.save(user);
                        return "user/nowfinalstep";
                }
            }
        }
        return null;
    }

}
