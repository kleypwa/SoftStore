package me.kley.soft_store.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("req/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/catalog")
    public String catalog() {
        return "catalog";
    }

    @GetMapping("/cabinet")
    public String cabinet() {
        return "cabinet";
    }

    @GetMapping("/bucket")
    public String bucket() {
        return "bucket";
    }

    @GetMapping("/markets")
    public String markets() {
        return "markets";
    }
}
