package com.example.taskworklife.controller;

import com.example.taskworklife.dto.user.UserLoginDto;
import com.example.taskworklife.dto.user.UserLoginResponseDto;
import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.ExceptionHandlingUser;
import com.example.taskworklife.exception.user.*;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Base64;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController extends ExceptionHandlingUser {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;

    }

    @PostMapping("/token")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserLoginResponseDto> tokenLogin(@RequestParam(required = true) String token) throws TokenParsingException, TokenIsLeegException, EmailIsNietGevonden {
        if (!StringUtils.isNotBlank(token)) {
            throw new TokenIsLeegException("De token is leeg");
        }
        User loginUser;
        try {
            String[] split = new String(Base64.getDecoder().decode(token)).split(":");
            if (split.length != 2) {
                throw new TokenParsingException("De token heeft geen twee waardes.");
            }
            loginUser = userService.findUserByEmail(split[0]);
            authenticate(split[0], split[1]);
        } catch (Exception e) {
            throw new TokenParsingException("Parsing de token gaf een error");
        }
        if (loginUser == null) {
            throw new EmailIsNietGevonden("De login email is niet gevonden");
        }
        return new ResponseEntity<>(userService.loginUser(loginUser), OK);
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto) throws EmailIsNietGevonden, LoginException {
        User loginUser = userService.findUserByEmail(userLoginDto.getEmail());
        if (loginUser == null) {
            throw new EmailIsNietGevonden("De login email is niet gevonden");
        }
        try {
            authenticate(userLoginDto.getEmail(), userLoginDto.getWachtwoord());
        } catch (Exception e) {
            throw new LoginException("Er is iets misgegaan met het inloggen");
        }

        return new ResponseEntity<>(userService.loginUser(loginUser), OK);
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserLoginResponseDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) throws EmailBestaatAl, RegisterErrorException {
        return new ResponseEntity<>(userService.register(userRegisterDto), OK);
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<UserLoginResponseDto>> getAllUsers() {

        return new ResponseEntity<>(userService.getUsers(), OK);
    }

    @GetMapping("/{voornaam}/{achterNaam}")
    @CrossOrigin(origins = "http://localhost:3000")
    public UserLoginResponseDto getSingleUser(@PathVariable String voornaam, @PathVariable String achterNaam) throws GebruikerNietGevondenExcepion {
        return userService.getSingleUser(voornaam, achterNaam);
    }

    @DeleteMapping("/{voornaam}/{achterNaam}/delete")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteSingleUser(@PathVariable String voornaam, @PathVariable String achterNaam) throws GebruikerNietGevondenExcepion {
        userService.deleteSingleUser(voornaam, achterNaam);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
