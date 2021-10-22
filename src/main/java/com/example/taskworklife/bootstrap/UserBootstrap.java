package com.example.taskworklife.bootstrap;

import com.example.taskworklife.models.user.User;
import com.example.taskworklife.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.taskworklife.enumeration.Role.ROLE_ADMIN;
import static com.example.taskworklife.enumeration.Role.ROLE_USER;

@Component
@Slf4j
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserBootstrap(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        userRepo.saveAll(getUsers());
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        User normalUser = new User();

        normalUser.setWachtwoord(passwordEncoder.encode("Pokemon!23"));
        normalUser.setNaam("jan");
        normalUser.setAchternaam("peter");
        normalUser.setEmail("pokemon@gmail.com");
        normalUser.setJoinDate(new Date());
        normalUser.setActive(true);
        normalUser.setNotLocked(true);
        normalUser.setRole(ROLE_USER.name());
        normalUser.setAuthorities(ROLE_USER.getAuthorities());
        normalUser.setLaatstIngelodgeDatum(new Date());
        users.add(normalUser);

        User adminUser = new User();
        adminUser.setNaam("admin");
        adminUser.setAchternaam("admin");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setJoinDate(new Date());
        adminUser.setActive(true);
        adminUser.setNotLocked(true);
        adminUser.setRole(ROLE_ADMIN.name());
        adminUser.setAuthorities(ROLE_ADMIN.getAuthorities());
        adminUser.setWachtwoord(passwordEncoder.encode("AdminUser!1"));
        users.add(adminUser);

        return users;
    }
}
