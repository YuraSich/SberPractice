package com.example.cds.service;

import com.example.cds.dto.ContentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CMSServiceImpl implements CMSService{


    public ContentDTO getContent(String contentGuid) {

        // Имитация вызова API CDS-сервиса
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ContentDTO contentDTO = generate(contentGuid);
        return contentDTO;
    }

    private ContentDTO generate(String contentGuid) {

        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        class Helper {
            String header;
            String backgroundImg;
            String text;
        }

        Helper helper = new Helper(
                "Текст заголовка",
                UUID.randomUUID().toString().substring(0, 5) + ".img",
                "Текст"
        );
        ObjectMapper objectMapper = new ObjectMapper();
        String str = "null";
        try {
            str = objectMapper.writeValueAsString(helper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ContentDTO contentDTO = new ContentDTO(contentGuid, str);
        return contentDTO;
    }
}
