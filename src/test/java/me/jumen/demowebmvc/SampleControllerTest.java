package me.jumen.demowebmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class SampleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloTest() throws Exception {
        mockMvc.perform(get("/sample/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
        ;
    }


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
        mockMvc.perform(get("/sample/regExp/jumen"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("helloRegExp jumen"))
        ;
    }

    // @GetMapping(value = "/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Test
    public void json() throws Exception {
        mockMvc.perform(get("/sample/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello json"))
        ;
    }

    // @GetMapping(value = "/header", headers = HttpHeaders.FROM)
    @Test
    public void headerTest() throws Exception {
        mockMvc.perform(get("/sample/header")
                .header("FROM", "HOME"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("header"))
        ;
    }

    // @GetMapping(value = "/notHeader", headers = "!" + HttpHeaders.FROM)
    @Test
    public void notHeaderTest() throws Exception {
        mockMvc.perform(get("/sample/notHeader")
                .header("FROM", "HOME"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("notHeader"))
        ;
    }

    //    @GetMapping(value = "/param", params = "param")
    @Test
    public void paramTest() throws Exception {
        mockMvc.perform(get("/sample/param")
                .param("param", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("param"))
        ;
    }

    @GetMapping(value = "/notParam", params = "!" + "param")
    @Test
    public void notParamTest() throws Exception {
        mockMvc.perform(get("/sample/notParam")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("notParam"))
        ;
    }

    // @GetMapping(value = "/param", params = "param=1")
    @Test
    public void paramAndValue() throws Exception {
        mockMvc.perform(get("/sample/paramAndValue")
                .param("param", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("paramAndValue"))
        ;
    }

    @Test
    public void headTest() throws Exception {
        mockMvc.perform(head("/sample/hello"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void optionsTest() throws Exception {
        mockMvc.perform(options("/sample/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().stringValues(HttpHeaders.ALLOW,
                        hasItems(containsString("GET"), containsString("HEAD"), containsString("OPTIONS"))))
        ;
    }

    @Test
    public void deleteEvent() throws Exception {
        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/events/2"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/events/3"))
                .andExpect(status().isOk());
    }

    @Test
    public void createEvent() throws Exception {
        mockMvc.perform(
                post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEvent() throws Exception {
        mockMvc.perform(
                put("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}