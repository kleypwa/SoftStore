package me.kley.soft_store.controllers;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.kley.soft_store.dto.RegistrationRequest;
import me.kley.soft_store.event.RegistrationCompleteEvent;
import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.models.User;
import me.kley.soft_store.service.UserService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    public String registerUser(RegistrationRequest request, HttpServletRequest servletRequest) {
        User user = userService.registerUser(request);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(servletRequest)));
        return "Success! Please, check your email to complete registration.";
    }

    public String applicationUrl(HttpServletRequest servletRequest) {
        return "http://" +
                servletRequest.getServerName() + ":" +
                servletRequest.getServerPort() +
                servletRequest.getContextPath();
    }
}
