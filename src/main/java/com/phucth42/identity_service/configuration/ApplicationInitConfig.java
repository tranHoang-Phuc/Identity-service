package com.phucth42.identity_service.configuration;

import com.phucth42.identity_service.entity.Role;
import com.phucth42.identity_service.entity.User;
import com.phucth42.identity_service.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(IUserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin") == null) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .build();
                userRepository.save(user);
                log.warn("Admin user created with username: admin and password: admin");
            }
        };
    }
}
