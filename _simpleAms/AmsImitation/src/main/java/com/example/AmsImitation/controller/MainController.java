package com.example.AmsImitation.controller;

import com.example.AmsImitation.controller.dto.ViewedListDto;
import com.example.AmsImitation.controller.dto.ViwedDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @PostMapping(value = "/viewed")
    public ResponseEntity<String> viwed(
            @RequestBody ViewedListDto viewedListDto
            ) throws InterruptedException {

        /// Тут какая то логика
        Thread.sleep(1000);
        viewedListDto.getViwedDtoList().forEach(
                viwedDto -> System.out.println(viwedDto.getUserGuid() + "\t" + viwedDto.getContentGuid())
        );
        System.out.println();
        // и отвечаем кодом 200
        return ResponseEntity.ok("OK");
    }
}
