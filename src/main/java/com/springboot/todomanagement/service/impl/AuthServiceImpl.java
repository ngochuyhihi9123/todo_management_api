package com.springboot.todomanagement.service.impl;

import com.springboot.todomanagement.dto.LoginDto;
import com.springboot.todomanagement.dto.RegisterDto;
import com.springboot.todomanagement.entity.Role;
import com.springboot.todomanagement.entity.User;
import com.springboot.todomanagement.exception.ToDoApiException;
import com.springboot.todomanagement.repository.RoleRepository;
import com.springboot.todomanagement.repository.UserRepository;
import com.springboot.todomanagement.security.JwtTokenProvider;
import com.springboot.todomanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new ToDoApiException(HttpStatus.BAD_REQUEST,"Email already exist");
        }
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new ToDoApiException(HttpStatus.BAD_REQUEST,"Username already exist");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()) );

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return "User Register Successfully";
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}
