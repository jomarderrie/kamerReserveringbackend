package com.example.taskworklife.kamer;

import com.example.taskworklife.controller.KamerController;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@WebMvcTest(controllers = KamerController.class)
@ActiveProfiles("test")
public class KamerIntegrationTest {
    @MockBean
    private KamerService kamerService;
    @MockBean
    UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getKamers() throws Exception {
        Kamer kamer2 = new Kamer();
        kamer2.setNaam("kamer2");
        kamer2.setStartTijd(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        kamer2.setSluitTijd(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        //kamer 1 reservering
        List<Reservering> reserveringListKamer2 = new ArrayList<>();

        Reservering reservering2Kamer1 = new Reservering();
        reservering2Kamer1.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));
        reservering2Kamer1.setEnd(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)));

        reserveringListKamer2.add(reservering2Kamer1);

        kamer2.setReservering(reserveringListKamer2);

//        Mockito.when(kamerService.getKamers()).thenReturn(Arrays.asList(kamer2));

        mockMvc.perform(MockMvcRequestBuilders.get("/kamer/all")).andExpect(MockMvcResultMatchers.status().is(200));
    }
}
