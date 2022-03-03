package com.example.taskworklife.user;

import com.example.taskworklife.service.kamer.KamerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public class UserControllerTest {
    @MockBean
    private KamerService kamerService;

    @Autowired
    private MockMvc mockMvc;
}
