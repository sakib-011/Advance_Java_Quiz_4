package com.sakib.quiz_4.controllers;

import com.sakib.quiz_4.models.Admin;
import com.sakib.quiz_4.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LoginRestController {

    private final JwtService jwtService;
    private  Integer requestCount = 0;

    public LoginRestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> getJwtAuthenticationToken(@RequestBody Admin admin , HttpServletResponse httpServletResponse){

        if(!admin.getUsername().isEmpty()){
            if(admin.getUsername().equals(admin.getPassword())){
                requestCount++;
                System.out.println("Request count : " + requestCount);
                String jwtToken = jwtService.generateJwtToken(admin.getUsername() , List.of("ADMIN" , "SUPER_ADMIN"));



                return ResponseEntity.ok(jwtToken);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
