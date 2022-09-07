package com.example.demo.controller;

import com.example.demo.config.JWTProvider;
import com.example.demo.model.dto.ErrorMessage;
import com.example.demo.model.dto.auth.JwtResponse;
import com.example.demo.model.dto.auth.UserLoginForm;
import com.example.demo.model.dto.auth.UserRegisterForm;
import com.example.demo.model.entity.auth.User;
import com.example.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class AuthRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UserRegisterForm userRegisterForm){
        if (!userRegisterForm.confirmPasswordMatch()) {
            ErrorMessage errorMessage = new ErrorMessage("Mật khẩu nhập lại không khớp!");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Optional<User> findUser = userService.findByUsername(userRegisterForm.getUsername());
        if (findUser.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Tên đăng nhập đã tồn tại!");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Optional<User> findUserByEmail = userService.findByEmail(userRegisterForm.getEmail());
        if (findUserByEmail.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Địa chỉ email đã tồn tại!");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(userRegisterForm.getEmail());
        user.setUsername(userRegisterForm.getUsername());
        user.setFullName(userRegisterForm.getFullName());
        user.setAddress(userRegisterForm.getAddress());
        user.setPassword(userRegisterForm.getPassword());
        user.setAvatar("default-avatar.jpg");
        this.userService.saveCustomer(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginForm loginForm){
        String inputEmail = loginForm.getEmail();
        String inputPassword = loginForm.getPassword();
        Optional<User> findUser = userService.findByEmail(inputEmail);

        if (!findUser.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Tài khoản không tồn tại");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        boolean matchPassword = passwordEncoder.matches(inputPassword, findUser.get().getPassword());
        if (!matchPassword) {
            ErrorMessage errorMessage = new ErrorMessage("Mật khẩu không đúng");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(findUser.get().getUsername(), inputPassword)); // tạo đối tượng Authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);  // lưu đối tượng Authentication vào ContextHolder
        String jwt = jwtProvider.generateTokenLogin(authentication);  // tạo token từ đối tượng Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = findUser.get();

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setId(currentUser.getId());
        jwtResponse.setToken(jwt);
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setRoles(userDetails.getAuthorities());
        jwtResponse.setEmail(inputEmail);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
