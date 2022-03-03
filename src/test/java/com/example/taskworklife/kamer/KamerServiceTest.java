package com.example.taskworklife.kamer;

import com.example.taskworklife.converter.KamerDtoToKamer;
import com.example.taskworklife.converter.KamerToKamerDto;
import com.example.taskworklife.converter.ReserveringDtoToReservering;
import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.repo.FileAttachmentRepo;
import com.example.taskworklife.repo.KamerRepo;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.kamer.KamerServiceImpl;
import com.example.taskworklife.service.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class KamerServiceTest {

    @Mock
    private KamerRepo kamerRepo;
    @Mock
    private KamerService kamerService;


    @Mock
    private KamerServiceImpl kamerServiceImpl;
    @Mock private KamerDtoToKamer kamerDtoToKamer;
    @Mock private ReserveringDtoToReservering reserveringDtoToReservering;

    @Mock private KamerToKamerDto kamerToKamerDto;

    @Mock private UserService userService;
    @Mock private FileAttachmentRepo fileAttachmentRepository;
    @Mock private FileService fileService;
    @Captor
    ArgumentCaptor<Kamer> kamerArgumentCaptor;

    @BeforeEach
    @WithMockUser()
    void setUp(){

        kamerServiceImpl = new KamerServiceImpl(kamerRepo,kamerDtoToKamer,kamerToKamerDto,reserveringDtoToReservering,fileService, fileAttachmentRepository,userService);
    }


    @Test
    void getKamers() {
        //when
//        Mockito.when(kamerService.getKamers(1,10,"kamer")).thenReturn();

        Page<Kamer> kamers = kamerService.getKamers(0, 10, "kamer");
        //then
        assertThat(kamers).isNull();

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
        Page<Kamer> pagedResponse = new PageImpl<Kamer>(List.of(kamer2));
        Mockito.when(kamerService.getKamers(0,10,"kamer")).thenReturn(pagedResponse);

        assertThat(kamerService.getKamers(0,10,"kamer").getContent().size()).isEqualTo(1);

        assertThat(kamerService.getKamers(0,10,"kamer").getContent().get(0)).isEqualTo(kamer2);


    }

    @Test
    void getAllKamerReservationsOnCertainDay() {
    }

    @Test
    void getKamerByNaam() {

    }

    @Test
    void maakNieuweKamerAan() {
        // given
        KamerDto kamer = new KamerDto();
        kamer.setNaam("standaard-kamer-naam");
        kamer.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        kamer.setSluit(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        // when


    }

    @Test
    void editKamer() {
    }

    @Test
    void deleteKamerByNaam() {
    }

    @Test
    void reserveerKamer() {
    }








}
