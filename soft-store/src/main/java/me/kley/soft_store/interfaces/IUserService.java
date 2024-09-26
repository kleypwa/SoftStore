package me.kley.soft_store.interfaces;

import me.kley.soft_store.dto.RegistrationRequest;
import me.kley.soft_store.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);
}
