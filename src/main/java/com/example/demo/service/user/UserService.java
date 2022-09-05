package com.example.demo.service.user;

import com.example.demo.model.dto.auth.UserPrincipal;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).get();
        return UserPrincipal.build(user);
    }

    @Override
    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User saveAdmin(User admin) {
        Optional<Role> roleOptional = this.roleService.findByName("ROLE_ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleOptional.get().getId(), roleOptional.get().getName()));
        String password = admin.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        admin.setPassword(encodedPassword);
        admin.setRoles(roles);
        return userRepository.save(admin);
    }

    @Override
    public User saveCustomer(User customer) {
        Optional<Role> roleOptional = this.roleService.findByName("ROLE_CUSTOMER");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleOptional.get().getId(), roleOptional.get().getName()));
        String password = customer.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        customer.setPassword(encodedPassword);
        customer.setRoles(roles);
        return userRepository.save(customer);
    }

    @Override
    public User saveMerchant(User merchant) {
        Optional<Role> roleOptional = this.roleService.findByName("ROLE_MERCHANT");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleOptional.get().getId(), roleOptional.get().getName()));
        String password = merchant.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        merchant.setPassword(encodedPassword);
        merchant.setRoles(roles);
        return userRepository.save(merchant);    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
