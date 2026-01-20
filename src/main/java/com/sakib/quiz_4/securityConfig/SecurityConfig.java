package com.sakib.quiz_4.securityConfig;

import com.sakib.quiz_4.customAuthentications.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtAuthenticationProvider jwtAuthenticationProvider) throws Exception{

        httpSecurity
                .csrf(csrf-> csrf.disable())
                .sessionManagement(se-> se.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( req->
                req
                        .requestMatchers("/auth/login" , "/api/**").permitAll()
                        .requestMatchers("/api/update/**" , "/api/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/authentication/**" , "/login" , "/").permitAll()


                        .anyRequest().authenticated()
        ).addFilterBefore(jwtAuthenticationProvider , UsernamePasswordAuthenticationFilter.class);

       return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return user -> User.withUsername(user).password(" ").build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
