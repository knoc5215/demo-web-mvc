package me.jumen.demowebmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class HandlerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getEvent() throws Exception {
        mockMvc.perform(get("/events/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
        ;
    }

    @Test
    public void getEventMatrix() throws Exception {
        mockMvc.perform(get("/events/matrix/1;name=jumen"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
        ;
    }

    @Test
    public void getEventRequestParam() throws Exception {
        mockMvc.perform(post("/events")
                    .param("name", "jumen")
                    .param("limit", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("jumen"))
                .andExpect(jsonPath("limit").value(10))
        ;
    }

    @Test
    public void getEventModel() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/eventsModel")
                .param("name", "jumen")
                .param("limit", "-100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());

        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();
        Map<String, Object> model = modelAndView.getModel();
        System.out.println(model.size());
    }

    @Test
    public void getEventMap() throws Exception {
        mockMvc.perform(post("/events/map")
                    .param("name", "jumen")
                    .param("limit", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("jumen"))
                .andExpect(jsonPath("limit").value(10))
        ;
    }

    @Test
    public void eventForm() throws Exception {
        mockMvc.perform(get("/events/form"))
                .andDo(print())
                .andExpect(view().name("events/form"))
                .andExpect(model().attributeExists("event"))
        ;
    }


}