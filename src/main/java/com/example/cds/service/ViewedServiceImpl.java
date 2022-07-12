package com.example.cds.service;

import com.example.cds.entitty.Content;
import com.example.cds.entitty.Users;
import com.example.cds.entitty.Viewed;
import com.example.cds.repository.ViewedRep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewedServiceImpl implements ViewedService{
    private final ViewedRep viewedRep;

    public ViewedServiceImpl(ViewedRep viewedRep) {
        this.viewedRep = viewedRep;
    }

    public Viewed setViewed(Users user, Content content){
        Viewed viewed = new Viewed();
        viewed.setContent(content);
        viewed.setUser(user);

        viewed = viewedRep.save(viewed);
        return viewed;
    }

    @Override
    public List<Viewed> getUnSended() {
        List<Viewed> byIsSendedFalse = viewedRep.findByIsSendedFalse();
        return byIsSendedFalse;
    }

    @Override
    public Viewed sended(Viewed viewed) {
        viewed.setIsSended(true);
        return viewedRep.save(viewed);
    }
}
