package com.example.taskworklife.service.user;

import com.example.taskworklife.converter.UserRegisterDtoToUser;
import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.user.EmailExistException;
import com.example.taskworklife.exception.user.EmailNotFoundException;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepo userRepository;
    private final UserRegisterDtoToUser userRegisterDtoToUserConverter;


    @Autowired
    public UserServiceImpl(UserRepo userRepository, UserRegisterDtoToUser userRegisterDtoToUserConverter) {
        this.userRepository = userRepository;
        this.userRegisterDtoToUserConverter = userRegisterDtoToUserConverter;

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
    public User register(UserRegisterDto userRegisterDto) throws EmailExistException {
        User userByEmail = null;
        userByEmail = userRepository.findUserByEmail(userRegisterDto.getEmail());
        if (userByEmail != null) {
            LOGGER.error("User already found with email: " + userRegisterDto.getEmail());
            throw new EmailExistException("User already found with email: " + userRegisterDto.getEmail());
        } else {
            userByEmail = userRegisterDtoToUserConverter.convert(userRegisterDto);
            LOGGER.info("Nieuwe gebruiker met email: " + userRegisterDto.getEmail());
            assert userByEmail != null;
            userRepository.save(userByEmail);
            return userByEmail;
        }
    }


    @Override
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(usersList::add);
        return usersList;
    }

    @Override
    public User findUserByEmail(String email) throws EmailNotFoundException {
        User userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail == null) {
            LOGGER.error("User not found with email: " + email);
            throw new EmailNotFoundException("Email not found");
        }
        return userByEmail;
    }


}
