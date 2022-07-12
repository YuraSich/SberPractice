package com.example.cds.service;

import com.example.cds.model.AdvertisingInfo;
import com.example.cds.entitty.Target;
import com.example.cds.entitty.Users;

import java.net.URISyntaxException;

public interface MainService {
    Target getTarget(Users user, String page);
    AdvertisingInfo getAdvertisingInfo(Target target);

    void sendViewedToAms() throws Exception;
}
