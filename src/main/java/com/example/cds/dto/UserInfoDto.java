package com.example.cds.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoDto {
    private String guid;
    private String SystemName;
    private String phone;
    private String email;
}
