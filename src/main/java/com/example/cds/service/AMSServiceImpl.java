package com.example.cds.service;


import com.example.cds.dto.TargetDTO;
import com.example.cds.entitty.Users;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


@Service
public class AMSServiceImpl implements AMSService {

    public TargetDTO getTarget(Users user, String page) {
        /// Вызов API AMS, для получения target
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TargetDTO targetDTO = this.generateTargetDTO(user, page);

        return targetDTO;
    }

    // Имитация вызова API
    private TargetDTO generateTargetDTO(Users user, String page) {
        TargetDTO target = new TargetDTO();
        target.setPage(page);
        target.setStartDate(
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace("-", "")
        );
        target.setEndDate(
                LocalDateTime.now().plusDays((long) (Math.random() * 10))
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace("-", "")
        );
        Random rnd = new Random();

        // generate offers
        for (int i = 0; i < 1 + Math.random() * 3; i++) {
            TargetDTO.Targets tmp = new TargetDTO.Targets();
            tmp.setUserGuid(user.getGuid());
            tmp.setOffers(new ArrayList<>());
            for (int j = 0; j < 1 + Math.random() * 3; j++) {
                TargetDTO.Targets.Offers offer = new TargetDTO.Targets.Offers(
                        UUID.randomUUID().toString(),
                        Math.abs(rnd.nextInt() % 99 + 1)
                );
                tmp.getOffers().add(offer);
            }
            target.getTarget().add(tmp);
        }
        return target;
    }
}
