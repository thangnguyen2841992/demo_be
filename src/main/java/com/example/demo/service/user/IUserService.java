package com.example.demo.service.user;

import com.example.demo.model.entity.auth.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends UserDetailsService {

    Iterable<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

    User saveAdmin(User admin);

    User saveCustomer(User customer);

    User saveMerchant(User merchant);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
