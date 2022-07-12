package com.example.cds.service;

import com.example.cds.dto.TargetDTO;
import com.example.cds.entitty.Users;

public interface AMSService {
    TargetDTO getTarget(Users user, String page);
}
