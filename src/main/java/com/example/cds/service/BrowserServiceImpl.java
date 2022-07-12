package com.example.cds.service;

import com.example.cds.dto.UserInfoDto;
import com.example.cds.entitty.Users;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class BrowserServiceImpl implements BrowserService{

    public UserInfoDto getUserInfo(Users user) {

        /// Тут вызов API, которое вернет нам информацию о пользователе( phone, email, systemname)
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String phone = generatePhone();
        String email = generateEmail();
        String systemName = generateName();

        UserInfoDto userInfoDto = new UserInfoDto(user.getGuid(), systemName, phone, email);

        return userInfoDto;
    }

    private String generateEmail() {
        return this.generateName() + "@gmail.com";
    }

    private String generateName() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private String generatePhone() {
        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);

        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

        String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);

        return phoneNumber;
    }
}
