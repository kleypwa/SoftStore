package me.kley.soft_store.service;

import lombok.RequiredArgsConstructor;
import lombok.var;
import me.kley.soft_store.dto.RegistrationRequest;
import me.kley.soft_store.exeption.UserAlreadyExistsException;
import me.kley.soft_store.interfaces.IUserService;
import me.kley.soft_store.models.User;
import me.kley.soft_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.email() +
                    "already exists");
        }
        var newUser = new User();
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        newUser.setUserName(request.userName());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
