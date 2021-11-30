package com.example.taskworklife.controller;

import com.example.taskworklife.converter.UserToUserLoginDto;
import com.example.taskworklife.dto.user.UserLoginDto;
import com.example.taskworklife.dto.user.UserLoginResponseDto;
import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.ExceptionHandlingUser;
import com.example.taskworklife.exception.user.EmailExistException;
import com.example.taskworklife.exception.user.EmailNotFoundException;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController extends ExceptionHandlingUser {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private UserToUserLoginDto userLoginResponseDtoConverter;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, UserToUserLoginDto userLoginResponseDtoConverter) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userLoginResponseDtoConverter = userLoginResponseDtoConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto) throws EmailNotFoundException {
        authenticate(userLoginDto.getEmail(), userLoginDto.getWachtwoord());
        User loginUser = userService.findUserByEmail(userLoginDto.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        UserLoginResponseDto loginConvertedUserDto = userLoginResponseDtoConverter.convert(loginUser);
        return new ResponseEntity<>(loginConvertedUserDto, OK);
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserLoginResponseDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) throws EmailExistException {
        User registeredUser = userService.register(userRegisterDto);
        UserLoginResponseDto registeredConvertedUserDto = userLoginResponseDtoConverter.convert(registeredUser);
        return new ResponseEntity<>(registeredConvertedUserDto, OK);
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<User>> getAllUsers(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

//    @GetMapping("/{naam}{achterNaam}")
//    public ResponseEntity<User> getUserByNaamAchterNaam(Principal principal){
//        return ResponseEntity(new User(), OK);
//    }




    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
