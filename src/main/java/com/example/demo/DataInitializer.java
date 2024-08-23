package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if roles already exist, to avoid duplications
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role guestRole = new Role();
            guestRole.setName("ROLE_GUEST");
            roleRepository.save(guestRole);

            // Create the admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);
            userRepository.save(admin);

            // Create the guest user
            User guest = new User();
            guest.setUsername("guest");
            guest.setPassword(passwordEncoder.encode("123456"));
            Set<Role> guestRoles = new HashSet<>();
            guestRoles.add(guestRole);
            guest.setRoles(guestRoles);
            userRepository.save(guest);

            System.out.println("Initialized database with default users and roles.");
        } else {
            System.out.println("Users and roles already exist, skipping initialization.");
        }
    }
}
