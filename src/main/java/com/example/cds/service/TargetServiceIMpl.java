package com.example.cds.service;


import com.example.cds.entitty.Target;
import com.example.cds.repository.TargetRep;
import org.springframework.stereotype.Service;

@Service
public class TargetServiceIMpl implements TargetService{

    private final TargetRep targetRep;

    public TargetServiceIMpl(TargetRep targetRep) {
        this.targetRep = targetRep;
    }

    public Target save(Target target){
        target = targetRep.save(target);
        return target;
    }
}
