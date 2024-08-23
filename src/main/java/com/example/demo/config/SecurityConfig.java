package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder()
                .username("guest")
                .password("{bcrypt}$2a$10$rCb5zuvO8ttF/eFzaAFPouRHthXhQwaVqYU.vAgpzx7W0ocPYiDGu")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$ZDt85aXSsTOB.zv4lsuNPujc8B0OqGQ8s/I2MBzqxQPDhnNzy8Vgu")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
