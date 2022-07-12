package com.example.cds.service;

import com.example.cds.dto.UserInfoDto;
import com.example.cds.entitty.Users;

public interface BrowserService {
    UserInfoDto getUserInfo(Users user);
}
