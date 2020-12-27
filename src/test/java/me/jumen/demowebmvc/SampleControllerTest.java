package me.jumen.demowebmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class SampleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloGet() throws Exception {
        mockMvc.perform(get("/sample/helloGet"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloGet"))
        ;

        mockMvc.perform(get("/sample/helloGet2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloGet"))
        ;

    }

    @Test
    public void helloPost() throws Exception {
        mockMvc.perform(post("/sample/helloPost"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloPost"))
        ;

    }

    // /hello/? - 한 글자
    @Test
    public void helloChar() throws Exception {
        mockMvc.perform(get("/sample/hello/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloChar"))
        ;
    }

    // /hello/* - 여러 글자
    @Test
    public void helloChars() throws Exception {
        mockMvc.perform(get("/sample/hello/5215"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloChars"))
        ;
    }

    // /hello/** - 여러 패스
    @Test
    public void helloPath() throws Exception {
        mockMvc.perform(get("/sample/hello/jumen/5215"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloPath"))
        ;
    }

    // /hello/{name} vs /hello/**
    // 가장 구체적으로 맵핑되는 핸들러가 선택된다
    @Test
    public void helloJumen() throws Exception {
        mockMvc.perform(get("/sample/hello/jumen"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello jumen"))
                .andExpect(handler().handlerType(SampleController.class))
                .andExpect(handler().methodName("helloJumen"))
        ;
    }

    // controller RequestMapping /sample
    @Test
    public void sample() throws Exception {
        mockMvc.perform(get("/sample/hello/jumen/5215"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloPath"))
        ;
    }

    // /regExp
    @Test
    public void helloRegExp() throws Exception {
        mockMvc.perform(get("/sample/jumen"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloRegExp jumen"))
        ;
    }
}