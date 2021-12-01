package com.example.taskworklife.service.user;

import com.example.taskworklife.dto.user.UserLoginDto;
import com.example.taskworklife.dto.user.UserLoginResponseDto;
import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.user.EmailExistException;
import com.example.taskworklife.exception.user.EmailNotFoundException;
import com.example.taskworklife.exception.user.RegisterErrorException;
import com.example.taskworklife.models.user.User;

import java.util.List;

public interface UserService {
    UserLoginResponseDto register(UserRegisterDto userRegisterDto) throws EmailExistException, RegisterErrorException;

    public List<UserLoginResponseDto> getUsers();

    UserLoginResponseDto loginUser(User User);
    User findUserByEmail(String email) throws EmailNotFoundException;

    UserLoginResponseDto getSingleUser(String voorNaam, String achterNaam);
}
