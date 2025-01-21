package com.puntoxpress.backend.service;

import com.puntoxpress.backend.model.Enties.User;
import com.puntoxpress.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User crear(User infoRequest) {
        infoRequest.setPassword(passwordEncoder.encode(infoRequest.getPassword()));
        return userRepository.save(infoRequest);
    }

    public Page<User> obtenerLista(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User actualizar(UUID id, User infoRequest) {
        if (userRepository.existsById(id)) {
            infoRequest.setId(id);
            return userRepository.save(infoRequest);
        }
        return null;
    }

    public boolean eliminar(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User buscarPorId(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User buscarPorEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
