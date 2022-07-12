package com.example.cds.service;

import com.example.cds.entitty.Content;
import com.example.cds.entitty.Users;
import com.example.cds.entitty.Viewed;

import java.util.List;

public interface ViewedService {
    Viewed setViewed(Users user, Content content);

    List<Viewed> getUnSended();

    Viewed sended(Viewed viewed);
}
