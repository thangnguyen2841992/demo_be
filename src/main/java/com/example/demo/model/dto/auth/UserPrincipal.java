package com.example.demo.model.dto.auth;

import com.example.demo.model.entity.auth.Role;
import com.example.demo.model.entity.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPrincipal implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private List<? extends GrantedAuthority> roles;

    public static UserPrincipal build(User user) {
        List<Role> roles = user.getRoles(); //Lấy ra role của user
        List<GrantedAuthority> authorities = new ArrayList<>(); //tạo một list quyền cho user principal
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName())); //thêm quyền vào list
        }
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
