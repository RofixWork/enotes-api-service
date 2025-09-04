package com.rofix.enotes_service.config;

import com.rofix.enotes_service.entity.Role;
import com.rofix.enotes_service.repository.RoleRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import org.slf4j.event.Level;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (!roleRepository.existsByNameIgnoreCase("admin") || !roleRepository.existsByNameIgnoreCase("user")) {
            List<Role> roles = new ArrayList<>(List.of(
                    Role.builder().name("ADMIN").build(),
                    Role.builder().name("USER").build()
            ));

            roleRepository.saveAll(roles);
            LoggerUtils.createLog(Level.INFO, getClass().getName(), "run", "Roles had been Saved in DB.");
        }
    }
}
