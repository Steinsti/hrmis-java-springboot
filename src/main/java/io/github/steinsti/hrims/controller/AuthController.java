package io.github.steinsti.hrims.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import io.github.steinsti.hrims.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import io.github.steinsti.hrims.model.Role;
import io.github.steinsti.hrims.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(org.springframework.ui.Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String showLoginForm(){
        return "auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return "redirect:/admin/dashboard";
        }else{
            return "redirect:/employee/dashboard";
        }
    }
}
    