package com.example.demo.config.security;


import com.example.demo.config.CustomAccessDeniedHandler;
import com.example.demo.config.JwtAuthenticationFilter;
import com.example.demo.config.RestAuthenticationEntryPoint;
import com.example.demo.model.entity.auth.Role;

import com.example.demo.model.entity.auth.User;
import com.example.demo.service.role.IRoleService;
import com.example.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;



    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() { //bean m?? h??a pass
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //l???y user t??? DB
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @PostConstruct
    public void init() {
      List<User> users = (List<User>) this.userService.findAll();
      List<Role> roles = (List<Role>) this.roleService.findAll();

        if (roles.isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleMerchant = new Role("ROLE_MERCHANT");
            roleService.save(roleMerchant);
            Role roleCustomer = new Role("ROLE_CUSTOMER");
            roleService.save(roleCustomer);
            Role roleShipper = new Role("ROLE_SHIPPER");
            roleService.save(roleShipper);
        }
        if (users.isEmpty()) {
            User admin = new User("admin","admin@gmail.com", "thuthuyda1");
            userService.saveAdmin(admin);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());//T??y ch???nh l???i th??ng b??o 401 th??ng qua class restEntryPoint
        http.authorizeRequests()
                .antMatchers("/login",
                        "/register", "/**").permitAll() // t???t c??? truy c???p ???????c
                .anyRequest().authenticated()  //c??c request c??n l???i c???n x??c th???c
                .and().csrf().disable(); // v?? hi???u h??a b???o v??? c???a csrf (ki???m so??t quy???n truy c???p)
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}