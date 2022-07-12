package com.example.cds.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class BrowserWantAdDTO {
    @NotBlank(message = "'page' cannot be blank")
    private String page;


    @NotBlank(message = "'user_guid' cannot be blank")
    @JsonProperty("user_guid")
    private  String userGuid;
}
