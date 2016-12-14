package com.github.zxkane.controller;

import com.github.zxkane.config.SecurityTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
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
@EnableSpringDataWebSupport
@ActiveProfiles("test")
@ContextConfiguration(classes = {SecurityTestConfiguration.class, Controller1.class})
@WebMvcTest({Controller1.class})
public class EnableSpringDataWebSupportWhileTesting {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithUserDetails(value = "staffUsername")
    public void requestWithPageable() throws Exception {
        PageRequest pageable = new PageRequest(0, 10);
        mvc.perform(MockMvcRequestBuilders.get("/path1/withPageable")
                .param("page", "0").param("size", String.valueOf(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andExpect(content().string("staff"));
    }
}
