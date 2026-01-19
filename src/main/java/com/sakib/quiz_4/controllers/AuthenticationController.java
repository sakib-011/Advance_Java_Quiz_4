package com.sakib.quiz_4.controllers;

import com.sakib.quiz_4.models.Admin;
import com.sakib.quiz_4.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
public class AuthenticationController {

    private final JwtService jwtService;


    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping({"/login" , "/"})
    public String login(Model model){

        model.addAttribute("admin" , new Admin());

        return "login";
    }


    @PostMapping("/login")
    public String adminLogin(@ModelAttribute Admin admin , HttpServletResponse httpServletResponse){

        if(!admin.getPassword().isEmpty()){
            if(admin.getUsername().equals(admin.getPassword())){

                String jwtToken = jwtService.generateJwtToken(admin.getUsername() , List.of("ADMIN" , "SUPER_ADMIN"));

                Cookie cookie = new Cookie("my-token" , jwtToken);
                cookie.setPath("/");
                cookie.setHttpOnly(true);

                httpServletResponse.addCookie(cookie);

                return "redirect:https://advanced-java-quiz-4-6c1a25.gitlab.io/";
            } else{
                return "redirect:/login?error";
            }

        } else {
            return "redirect:/login";
        }

    }


    @GetMapping("/api/authentication/{username}/{password}")
    public String authenticate(@PathVariable String username , @PathVariable String password, HttpServletResponse httpServletResponse){

        if(username.equals(password)){

            String jwtToken =  jwtService.generateJwtToken(username , List.of("ADMIN" , "SUPER_ADMIN"));

            Cookie cookie = new Cookie("my-token" , jwtToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            httpServletResponse.addCookie(cookie);

            return "redirect:https://advanced-java-quiz-4-6c1a25.gitlab.io/";

        }

        return "redirect:/api/authentication";
    }
}
