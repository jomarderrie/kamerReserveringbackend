package com.example.taskworklife.converter.user;

import com.example.taskworklife.dto.user.UserLoginResponseDto;
import com.example.taskworklife.dto.user.UserProfileUpdateDto;
import com.example.taskworklife.exception.user.EmailIsNietJuist;
import com.example.taskworklife.exception.user.NaamBestaatNiet;
import com.example.taskworklife.exception.user.NaamTeKleinException;
import com.example.taskworklife.models.user.User;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserUpdateDtoToUserDto  implements Converter<UserProfileUpdateDto, User> {
    private BCryptPasswordEncoder passwordEncoder;
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    @Autowired
    public UserUpdateDtoToUserDto(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @SneakyThrows
    @Override
    public User convert(UserProfileUpdateDto source) {
        User user =new User();
        if (!isValid(source.getEmail())) {
            throw new EmailIsNietJuist("Email is niet juist");
        }
        //check of naam niet leeg is of kleiner dan 3 in size is
        if (!StringUtils.isNotBlank(source.getFirstName())){
            throw new NaamBestaatNiet("Naam bestaat niet");
        }
        if(source.getFirstName().length()<3){
            throw new NaamTeKleinException("Naam is te klein");
        }
        //check of achternaam niet leeg is of kleiner dan 3 in size is
        if (!StringUtils.isNotBlank(source.getLastName())){
            throw new NaamBestaatNiet("Achternaam bestaat niet");
        }
        if(source.getLastName().length()<3){
            throw new NaamTeKleinException("Achternaam is te klein");
        }
        user.setAchternaam(source.getLastName());
        user.setNaam(source.getFirstName());
        user.setEmail(source.getEmail());
        user.setWachtwoord(passwordEncoder.encode(source.getPassword()));
        return user;
    }

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
