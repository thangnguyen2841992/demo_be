package com.example.demo.service.role;

import com.example.demo.model.entity.Role;


import java.util.Optional;

public interface IRoleService {
    Iterable<Role> findAll();

    Optional<Role> findById(Long id);

    Role save(Role role);

    void deleteById(Long id);

    Optional<Role> findByName(String name);

}
