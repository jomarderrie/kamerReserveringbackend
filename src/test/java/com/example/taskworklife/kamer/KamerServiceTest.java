package com.example.taskworklife.kamer;

import com.example.taskworklife.converter.KamerDtoToKamer;
import com.example.taskworklife.converter.KamerToKamerDto;
import com.example.taskworklife.converter.ReserveringDtoToReservering;
import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.exception.kamer.KamerBestaatAl;
import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.repo.FileAttachmentRepo;
import com.example.taskworklife.repo.KamerRepo;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.kamer.KamerServiceImpl;
import com.example.taskworklife.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class KamerServiceTest {

    @Mock
    private KamerRepo kamerRepo;
    @Mock
    private KamerService kamerService;


    @Mock
    private KamerServiceImpl kamerServiceImpl;
    @Mock
    private KamerDtoToKamer kamerDtoToKamer;
    @Mock
    private ReserveringDtoToReservering reserveringDtoToReservering;

    @Mock
    private KamerToKamerDto kamerToKamerDto;

    @Mock
    private UserService userService;
    @Mock
    private FileAttachmentRepo fileAttachmentRepository;
    @Mock
    private FileService fileService;
    KamerTestHelper kamerTestHelper = new KamerTestHelper();
    @Captor
    ArgumentCaptor<Kamer> kamerArgumentCaptor;


    @BeforeEach
    @WithMockUser()
    void setUp() throws KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException {

        kamerServiceImpl = new KamerServiceImpl(kamerRepo, kamerDtoToKamer, kamerToKamerDto, reserveringDtoToReservering, fileService, fileAttachmentRepository, userService);
//        when(kamerServiceImpl.getKamerByNaam("testKamer")).thenReturn(kamerTestHelper.krijgKamers().get(0));
//        when(kamerRepo.findByNaam("testKamer")).thenReturn(kamerTestHelper.krijgKamers().get(0));
    }


    @Test
    void getKamers() {
        //when
//        Mockito.when(kamerService.getKamers(1,10,"kamer")).thenReturn();
        when(kamerServiceImpl.getKamers(0, 10, "naam")).thenReturn(new PageImpl<Kamer>(new ArrayList<>()));

        Page<Kamer> kamers = kamerServiceImpl.getKamers(0, 10, "naam");
        //then
        assertThat(kamerServiceImpl.getKamers(0, 10, "naam").getContent()).isEqualTo(new ArrayList<>());

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
        when(kamerServiceImpl.getKamers(0, 10, "naam")).thenReturn(pagedResponse);

        assertThat(kamerServiceImpl.getKamers(0, 10, "naam").getContent().size()).isEqualTo(1);

        assertThat(kamerServiceImpl.getKamers(0, 10, "naam").getContent().get(0)).isEqualTo(kamer2);


    }

    @Test
    void getAllKamerReservationsOnCertainDay() throws ParseException, KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = new Date(formatter.parse("01-01-1990").getTime());
        when(kamerRepo.findByNaam("testKamer")).thenReturn(kamerTestHelper.krijgKamers().get(0));

        when(kamerServiceImpl.getKamerByNaam("testKamer")).thenReturn(kamerTestHelper.krijgKamers().get(0));

//        var xs = Optional.of(List.of(Object));
        var a = Optional.of(new ArrayList<Object>());
        when(kamerRepo.findByNaamAndGetAllRoomsOnASpecifiedDay(date, "testKamer")).thenReturn(a);

        when(kamerServiceImpl.getAllKamerReservatiesOpEenBepaaldeDag("testKamer", date)).thenReturn(new ArrayList<>());


        List<Object> testKamer = kamerServiceImpl.getAllKamerReservatiesOpEenBepaaldeDag("testKamer", date);


        assertEquals(testKamer.size(), 0);


    }

    @Test
    void getKamerByNaam() throws KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException {
        assertThrows(KamerNaamNotFoundException.class,
                () ->
                        kamerServiceImpl.getKamerByNaam(""),
                "to throw error"
        );
        assertThrows(KamerNaamNotFoundException.class,
                () ->
                        kamerServiceImpl.getKamerByNaam(null),
                "to throw error"
        );

        assertThrows(KamerNaamLengteIsTeKlein.class,
                () ->
                        kamerServiceImpl.getKamerByNaam("ak"),
                "to throw error"
        );

        assertThrows(KamerIsNietGevonden.class,
                () ->
                        kamerServiceImpl.getKamerByNaam("bigroom"),
                "to throw error"
        );


        when(kamerRepo.findByNaam("kamer2")).thenReturn(kamerTestHelper.krijgKamers().get(0));
        Kamer kamer2 = kamerServiceImpl.getKamerByNaam("kamer2");
        assertEquals(kamer2.getNaam(), "kamer2");

    }

    @Test
    void maakNieuweKamerAan() {
        // given

        KamerDto kamerDto = new KamerDto();

        kamerDto.setNaam("kamer2");
        kamerDto.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        kamerDto.setSluit(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));


        when(kamerRepo.findByNaam("kamer2")).thenReturn(kamerTestHelper.krijgKamers().get(0));

        assertThrows(KamerBestaatAl.class,
                () ->
                        kamerServiceImpl.maakNieuweKamerAan(kamerDto),
                "to throw error"
        );

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
