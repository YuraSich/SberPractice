package com.example.cds.service;

import com.example.cds.dto.UserInfoDto;
import com.example.cds.entitty.Users;
import com.example.cds.exceptions.UserNotFoundException;

public interface UserService {
    Users getUserByID(String id);
    Users findUserById(String id) throws UserNotFoundException;
    Users setUserInfo(Users user, UserInfoDto userInfo);
}
