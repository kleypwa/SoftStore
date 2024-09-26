package me.kley.soft_store.dto;

import org.hibernate.annotations.NaturalId;

public record RegistrationRequest(
        String userName,
        String email,
        String password,
        String role) {}


