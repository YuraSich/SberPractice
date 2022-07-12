package com.example.cds;


import com.example.cds.entitty.Viewed;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {

        String JSON = "{\n" +
                "\t\"page\" : \"Main_PAGE\"\n" +
                "}";

        MvcResult result = mockMvc.perform(
                post("/getAd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Matchers.hasKey("validationErrors")))
                .andExpect(jsonPath("$.validationErrors", Matchers.hasKey("userGuid")))
                .andExpect(jsonPath("$.validationErrors.userGuid", equalTo("'user_guid' cannot be blank")))
                .andReturn();
        // OR
//        String contentAsString = result.getResponse().getContentAsString();
//
//        Map<String,Object> map = new ObjectMapper().readValue(contentAsString, HashMap.class);
//
//
//
//        assertThat(map, hasKey("validationErrors"));
//        Map<String,Object> validationErrorsMap = (Map<String, Object>) map.get("validationErrors");
//        assertThat(validationErrorsMap, hasKey("userGuid"));

        System.out.println();
    }


}
