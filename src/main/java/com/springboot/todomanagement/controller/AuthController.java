package com.springboot.todomanagement.controller;

import com.springboot.todomanagement.dto.JwtAuthResponse;
import com.springboot.todomanagement.dto.LoginDto;
import com.springboot.todomanagement.dto.RegisterDto;
import com.springboot.todomanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String res = authService.register(registerDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.CREATED);
    }

//    @GetMapping("/message")
//    public String privateMessage(@AuthenticationPrincipal OAuth2User user){
//        System.out.println("Hello world");
//        System.out.println(user.getName());
//        user.getAttribute("name");
//        return user.getName();
//    }
}
