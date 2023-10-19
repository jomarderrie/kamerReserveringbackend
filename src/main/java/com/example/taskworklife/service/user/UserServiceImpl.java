package com.example.taskworklife.service.user;

import com.example.taskworklife.converter.user.UserRegisterDtoToUser;
import com.example.taskworklife.converter.user.UserToUserLoginDto;
import com.example.taskworklife.converter.user.UserUpdateDtoToUserDto;
import com.example.taskworklife.dto.user.UserLoginResponseDto;
import com.example.taskworklife.dto.user.UserProfileUpdateDto;
import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.global.FieldIsEmptyException;
import com.example.taskworklife.exception.user.*;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.repo.UserRepo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.taskworklife.enumeration.Role.ROLE_USER;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepo userRepository;
    private final UserRegisterDtoToUser userRegisterDtoToUserConverter;
    private UserToUserLoginDto userLoginResponseDtoConverter;
    private UserUpdateDtoToUserDto userUpdateDtoToUserDto;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, UserRegisterDtoToUser userRegisterDtoToUserConverter, UserToUserLoginDto userLoginResponseDtoConverter, UserUpdateDtoToUserDto userUpdateDtoToUserDto) {
        this.userRepository = userRepository;
        this.userRegisterDtoToUserConverter = userRegisterDtoToUserConverter;
        this.userLoginResponseDtoConverter = userLoginResponseDtoConverter;
        this.userUpdateDtoToUserDto = userUpdateDtoToUserDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail == null) {
            LOGGER.error("No user found with " + email);
            throw new UsernameNotFoundException("Not found!");
        } else {
            userByEmail.setLaatstIngelodgeDatumDisplay(userByEmail.getLaatstIngelodgeDatum());
            userByEmail.setLaatstIngelodgeDatum(new Date());
            userRepository.save(userByEmail);
            UserPrincipal userPrincipal = new UserPrincipal(userByEmail);
            LOGGER.info("Returning found user by username: " + email);
            return userPrincipal;
        }
    }

    @Override
    public UserLoginResponseDto register(UserRegisterDto userRegisterDto) throws EmailBestaatAl, RegisterErrorException {
        User userByEmail = null;
        userByEmail = userRepository.findUserByEmail(userRegisterDto.getEmail());
        if (userByEmail != null) {
            LOGGER.error("User already found with email: " + userRegisterDto.getEmail());
            throw new EmailBestaatAl("User already found with email: " + userRegisterDto.getEmail());
        } else {
            userByEmail = userRegisterDtoToUserConverter.convert(userRegisterDto);
            LOGGER.info("Nieuwe gebruiker met email: " + userRegisterDto.getEmail());
            assert userByEmail != null;
            UserLoginResponseDto convertedRegisterdUser = userLoginResponseDtoConverter.convert(userByEmail);
            userRepository.save(userByEmail);
            if (convertedRegisterdUser != null) {
                return convertedRegisterdUser;
            } else {
                throw new RegisterErrorException("Er is een error met het registeren");
            }
        }
    }

    @Override
    public List<UserLoginResponseDto> getUsers() {
        List<UserLoginResponseDto> usersList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(p -> {
            usersList.add(userLoginResponseDtoConverter.convert(p));
        });
        return usersList;
    }

    @Override
    public UserLoginResponseDto loginUser(User user) {
        return userLoginResponseDtoConverter.convert(user);
    }

    @Override
    public User findUserByEmail(String email) throws EmailIsNietGevonden {
        User userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail == null) {
            LOGGER.error("User not found with email: " + email);
            throw new EmailIsNietGevonden("Email not found");
        }
        return userByEmail;
    }

    @Override
    public UserLoginResponseDto getSingleUser(String voorNaam, String achterNaam) throws GebruikerNietGevondenExcepion {
        User userByAchternaamAndAndNaam = userRepository.findUserByAchternaamAndAndNaam(achterNaam, voorNaam);
        if (userByAchternaamAndAndNaam != null) {
            return userLoginResponseDtoConverter.convert(userByAchternaamAndAndNaam);
        } else {
            throw new GebruikerNietGevondenExcepion("Gebruiker is niet gevonden");
        }
    }

    @Override
    public void deleteSingleUser(String voorNaam, String achterNaam) throws GebruikerNietGevondenExcepion {
        User userByAchternaamAndNaam = userRepository.findUserByAchternaamAndAndNaam(achterNaam, voorNaam);
        if (userByAchternaamAndNaam != null) {
            if (userByAchternaamAndNaam.getId() != null) {
                LOGGER.info("deleted gebruiker met voor en achter naam " + voorNaam + " " + achterNaam);
                userRepository.deleteById(userByAchternaamAndNaam.getId());
            }
        } else {
            throw new GebruikerNietGevondenExcepion("Gebruiker is niet gevonden");
        }
    }

    @Override
    public void updateProfile(User user, UserProfileUpdateDto userProfileUpdateDto) throws FieldIsEmptyException, EmailBestaatAl {
        if (!StringUtils.isNotBlank(userProfileUpdateDto.getFirstName())||StringUtils.isNotBlank(userProfileUpdateDto.getPassword()) ||StringUtils.isNotBlank(userProfileUpdateDto.getEmail())
         || StringUtils.isNotBlank(userProfileUpdateDto.getPassword())) {
            throw new FieldIsEmptyException("Field not found");
        }
        User userByEmail = userRepository.findUserByEmail(userProfileUpdateDto.getEmail());
        if (userByEmail == null){
            User userUpdateDto = userUpdateDtoToUserDto.convert(userProfileUpdateDto);
            if (userUpdateDto != null) {
                userUpdateDto.setJoinDate(user.getJoinDate());
            }
            userUpdateDto.setActive(user.isActive());
            userUpdateDto.setNotLocked(user.isNotLocked());
            userUpdateDto.setRole(ROLE_USER.name());
            userUpdateDto.setAuthorities(ROLE_USER.getAuthorities());
        } else {
            LOGGER.error("User already found with email: " + userProfileUpdateDto.getEmail());
            throw new EmailBestaatAl("User already found with email: " + userProfileUpdateDto.getEmail());
        }
    }
}
