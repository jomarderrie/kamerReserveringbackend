package com.example.taskworklife.converter;

import com.example.taskworklife.config.SecurityConfiguration;
import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.user.TermsNotAcceptedException;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.repo.UserRepo;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.taskworklife.enumeration.Role.ROLE_USER;

@Component
public class UserRegisterDtoToUser implements Converter<UserRegisterDto, User> {
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRegisterDtoToUser(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @SneakyThrows
    @Synchronized
    @Nullable
    @Override
    public User convert(UserRegisterDto source) {
        final User user = new User();
        if (!source.isTerms()){
            throw new TermsNotAcceptedException("Terms arent accepted");
        }
        user.setWachtwoord(passwordEncoder.encode(source.getWachtwoord()));
        if (StringUtils.isNotBlank(source.getNaam())){

        }
        user.setNaam(source.getNaam());
        user.setAchternaam(source.getAchterNaam());
        user.setEmail(source.getEmail());
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setLaatstIngelodgeDatum(new Date());
        return user;
    }



}
