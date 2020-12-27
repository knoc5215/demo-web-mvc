package me.jumen.demowebmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SampleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloGet() throws Exception {
        mockMvc.perform(get("/helloGet"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloGet"))
        ;

    }

    @Test
    public void helloPost() throws Exception {
        mockMvc.perform(post("/helloPost"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloPost"))
        ;

    }
}