package me.kley.soft_store.controllers;


import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.repository.AppUserRepository;
import me.kley.soft_store.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping(value = "/signup", consumes = "application/json")
    public AppUser createUser(@RequestBody AppUser user) {
        return appUserService.createUser(user);
    }
}
