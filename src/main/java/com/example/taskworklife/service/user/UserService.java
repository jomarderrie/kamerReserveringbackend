package com.example.taskworklife.service.user;

import com.example.taskworklife.dto.user.UserRegisterDto;
import com.example.taskworklife.exception.user.EmailExistException;
import com.example.taskworklife.exception.user.EmailNotFoundException;
import com.example.taskworklife.models.user.User;

import java.util.List;

public interface UserService {
    User register(UserRegisterDto userRegisterDto) throws EmailExistException;

    public List<User> getUsers();

    User findUserByEmail(String email) throws EmailNotFoundException;

}
