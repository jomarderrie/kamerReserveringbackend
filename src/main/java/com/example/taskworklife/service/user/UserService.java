package com.example.taskworklife.service.user;

import com.example.taskworklife.dto.user.UserLoginResponseDto;
import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.user.EmailBestaatAl;
import com.example.taskworklife.exception.user.EmailIsNietGevonden;
import com.example.taskworklife.exception.user.GebruikerNietGevondenExcepion;
import com.example.taskworklife.exception.user.RegisterErrorException;
import com.example.taskworklife.models.user.User;

import java.util.List;

public interface UserService {
    UserLoginResponseDto register(UserRegisterDto userRegisterDto) throws EmailBestaatAl, RegisterErrorException;

    public List<UserLoginResponseDto> getUsers();

    UserLoginResponseDto loginUser(User User);
    User findUserByEmail(String email) throws EmailIsNietGevonden;

    UserLoginResponseDto getSingleUser(String voorNaam, String achterNaam) throws GebruikerNietGevondenExcepion;

    void deleteSingleUser(String voorNaam, String achterNaam) throws GebruikerNietGevondenExcepion;
}
