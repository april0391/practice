package edu.demo.controller;

import edu.demo.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(HomeController.class)
//@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
//    @WithMockUser(username = "user", roles = "USER")
    void homeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("ok"));
    }

}