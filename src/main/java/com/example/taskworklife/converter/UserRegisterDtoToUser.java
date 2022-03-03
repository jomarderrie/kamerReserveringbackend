package com.example.taskworklife.converter;

import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.user.EmailIsNietJuist;
import com.example.taskworklife.exception.user.NaamBestaatNiet;
import com.example.taskworklife.exception.user.NaamTeKleinException;
import com.example.taskworklife.exception.user.TermenNietGeaccepteerd;
import com.example.taskworklife.models.user.User;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.taskworklife.enumeration.Role.ROLE_USER;

@Component
public class UserRegisterDtoToUser implements Converter<UserRegisterDto, User> {
    private BCryptPasswordEncoder passwordEncoder;
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
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
        //check terms
        if (!source.isTerms()){
            throw new TermenNietGeaccepteerd("Terms arent accepted");
        }
        //check wachtwoord with regex
        user.setWachtwoord(passwordEncoder.encode(source.getWachtwoord()));

        //check of naam niet leeg is of kleiner dan 3 in size is
        if (StringUtils.isNotBlank(source.getNaam())){
            throw new NaamBestaatNiet("Naam bestaat niet");
        }
        if(source.getNaam().length()<3){
            throw new NaamTeKleinException("Naam is te klein");
        }
        user.setNaam(source.getNaam());
        //check of achternaam niet leeg is of kleiner dan 3 in size is
        if (!StringUtils.isNotBlank(source.getAchterNaam())){
            throw new NaamBestaatNiet("Achternaam bestaat niet");
        }
        if(source.getAchterNaam().length()<3){
            throw new NaamTeKleinException("Achternaam is te klein");
        }
        user.setAchternaam(source.getAchterNaam());

        if (!isValid(source.getEmail())) {
            throw new EmailIsNietJuist("Email is niet juist");
        }

        user.setEmail(source.getEmail());
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());

        return user;
    }

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
