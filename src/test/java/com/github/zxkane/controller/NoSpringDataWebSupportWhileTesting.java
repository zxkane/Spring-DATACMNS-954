package com.github.zxkane.controller;

import com.github.zxkane.config.SecurityTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kane on 2016/12/14.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {SecurityTestConfiguration.class, Controller1.class})
@WebMvcTest({Controller1.class})
public class NoSpringDataWebSupportWhileTesting {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithUserDetails(value = "staffUsername")
    public void requestWithoutPageable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/path1/withoutPageable"))
                .andExpect(status().isOk())
                .andExpect(content().string("staff"));
    }
}
