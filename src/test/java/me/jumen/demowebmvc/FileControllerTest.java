package me.jumen.demowebmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 모든 bean은 등록해주지만, mockMvc를 자동으로 만들어주지 않는다
@AutoConfigureMockMvc
        // mockMvc 주입받기
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fileUploadTest() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", "test.txt", "text/plain", "hello file".getBytes());
        mockMvc.perform(multipart("/file").file(mockMultipartFile))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
        ;
    }
}