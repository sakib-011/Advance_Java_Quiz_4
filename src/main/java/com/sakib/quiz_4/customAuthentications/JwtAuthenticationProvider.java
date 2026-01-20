package com.sakib.quiz_4.customAuthentications;

import com.sakib.quiz_4.services.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationProvider extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){
                System.out.println("Cookie : " + cookie.getName() + " :: "+ cookie.getValue());
                if(cookie.getName().equals("my-token")){
                    System.out.println("Token : " + cookie.getValue());
                    token = cookie.getValue();
                }
            }
        }
        if(token == null){
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request ,response);
            return;
        } else{
            try{
                Claims claims = jwtService.parseJwtToken(token);
                String username = jwtService.getUsername(token);
                List<String> roles = jwtService.getRoles(token);
                List<SimpleGrantedAuthority> ROLES = new ArrayList<>();

                for(String role : roles){
                    ROLES.add(new SimpleGrantedAuthority("ROLE_" + role));
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(username , null , ROLES);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request , response);
    }
}
