package com.example.taskworklife.bootstrap;

import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.PriorityOrdered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.taskworklife.enumeration.Role.ROLE_ADMIN;
import static com.example.taskworklife.enumeration.Role.ROLE_USER;

@Component
@Slf4j
@Profile({"test", "dev", "default"})
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent>,PriorityOrdered {
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
        User normaleGebruiker = new User();
        List<Reservering> reserveringListKamer2 = new ArrayList<>();

        normaleGebruiker.setReserveringArrayList(reserveringListKamer2);
        normaleGebruiker.setWachtwoord(passwordEncoder.encode("Pokemon!23"));
        normaleGebruiker.setNaam("jan");
        normaleGebruiker.setAchternaam("peter");
        normaleGebruiker.setEmail("pokemon@gmail.com");
        normaleGebruiker.setJoinDate(new Date());
        normaleGebruiker.setActive(true);
        normaleGebruiker.setNotLocked(true);
        normaleGebruiker.setRole(ROLE_USER.name());
        normaleGebruiker.setAuthorities(ROLE_USER.getAuthorities());
        normaleGebruiker.setLaatstIngelodgeDatum(new Date());

        users.add(normaleGebruiker);

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

    @Override
    public int getOrder() {
        return 10;
    }
}
