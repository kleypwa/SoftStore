package me.kley.soft_store.controllers;


import me.kley.soft_store.models.AppUser;
import me.kley.soft_store.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/appusers")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getAppUserById(@PathVariable Long id) {
        Optional<AppUser> appUser = appUserService.findById(id);
        return appUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}