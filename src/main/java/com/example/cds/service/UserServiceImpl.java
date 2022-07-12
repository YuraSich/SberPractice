package com.example.cds.service;

import com.example.cds.dto.UserInfoDto;
import com.example.cds.entitty.Users;
import com.example.cds.exceptions.UserNotFoundException;
import com.example.cds.repository.UserRep;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRep userRep;

    public UserServiceImpl(UserRep userRep) {
        this.userRep = userRep;
    }

    public Users getUserByID(String id) {
        Optional<Users> byGuid = userRep.findByGuid(id);
        return byGuid.orElseGet(
                () -> {
                    Users user = new Users();
                    user.setGuid(id);
                    user = userRep.save(user);
                    return user;
                }
        );
    }

    public Users findUserById(String id) throws UserNotFoundException {
        Optional<Users> user = userRep.findByGuid(id);
        return user.orElseThrow(
                () -> new UserNotFoundException("user with id = '" + id + "' not found")
        );
    }

    public Users setUserInfo(Users user, UserInfoDto userInfo) {
        user.setSystemName(userInfo.getSystemName());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());

        return userRep.save(user);
    }

}
