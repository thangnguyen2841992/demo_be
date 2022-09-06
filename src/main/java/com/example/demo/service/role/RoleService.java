package com.example.demo.service.role;

import com.example.demo.model.entity.auth.Role;
import com.example.demo.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Iterable<Role> findAll() {
        return this.roleRepository.findAll() ;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        this.roleRepository.deleteById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
