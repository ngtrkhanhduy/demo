// DataInitializer.java
package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
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
        initializeRoles();
        initializeAdminUser();
    }

    private void initializeRoles() {
        // Check if roles exist, if not create them
        if (!roleRepository.findByName("ADMIN").isPresent()) {
            Role adminRole = new Role("ADMIN");
            roleRepository.save(adminRole);
        }

        if (!roleRepository.findByName("USER").isPresent()) {
            Role userRole = new Role("USER");
            roleRepository.save(userRole);
        }
    }

    private void initializeAdminUser() {
        // Check if the admin user already exists
        if (!userRepository.findByUsername("admin").isPresent()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Encrypt the password
            adminUser.setEmail("admin@example.com");

            // Assign the ADMIN role to this user
            Optional<Role> adminRole = roleRepository.findByName("ADMIN");
            Optional<Role> userRole = roleRepository.findByName("USER");

            Set<Role> roles = new HashSet<>();
            adminRole.ifPresent(roles::add); // Add ADMIN role
            userRole.ifPresent(roles::add); // Optionally, add USER role as well

            adminUser.setRoles(roles);
            userRepository.save(adminUser);

            System.out.println("Admin user created: username = admin, password = admin123");
        }
    }

    private void initializeDefaultUser() {
        if (!userRepository.findByUsername("user").isPresent()) {
            User regularUser = new User();
            regularUser.setUsername("user");
            regularUser.setPassword(passwordEncoder.encode("user123"));
            regularUser.setEmail("user@example.com");

            Optional<Role> userRole = roleRepository.findByName("USER");
            Set<Role> roles = new HashSet<>();
            userRole.ifPresent(roles::add);

            regularUser.setRoles(roles);
            userRepository.save(regularUser);

            System.out.println("Regular user created: username = user, password = user123");
        }
    }

}
