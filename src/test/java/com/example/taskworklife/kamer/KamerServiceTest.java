package com.example.taskworklife.kamer;

import com.example.taskworklife.converter.KamerDtoToKamer;
import com.example.taskworklife.converter.KamerToKamerDto;
import com.example.taskworklife.converter.ReserveringDtoToReservering;
import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.dto.reservation.ReservatieDto;
import com.example.taskworklife.exception.kamer.*;
import com.example.taskworklife.exception.user.EmailIsNietGevonden;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.repo.FileAttachmentRepo;
import com.example.taskworklife.repo.KamerRepo;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.kamer.KamerServiceImpl;
import com.example.taskworklife.service.user.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

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



    @MockBean
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
//        kamerDtoToKamer = new KamerDtoToKamer();
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

        when(kamerRepo.findByNaamAndGetAllRoomsOnASpecifiedDay(date, "testKamer")).thenReturn(Optional.of(new ArrayList<>()));

        List<ReservatieDto> emptyTestKamer = kamerServiceImpl.getAllKamerReservatiesOpEenBepaaldeDag("testKamer", date);

        assertEquals(0, emptyTestKamer.size());

        when(kamerRepo.findByNaamAndGetAllRoomsOnASpecifiedDay(date, "testKamer")).thenReturn(Optional.ofNullable((kamerTestHelper.krijgReservaties())));

        List<ReservatieDto> kekKamer = kamerServiceImpl.getAllKamerReservatiesOpEenBepaaldeDag("testKamer", date);

        assertEquals(1, kekKamer.size());
        assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)), kekKamer.get(0).getStart());
        assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)), kekKamer.get(0).getEnd());
        assertEquals("peter", kekKamer.get(0).getNaam());
        assertEquals("jan", kekKamer.get(0).getAchterNaam());
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
    void kamerDtoToKamer() {
        KamerDto kamerDto = new KamerDto();

        kamerDto.setNaam("");
        assertThrows(KamerNaamNotFoundException.class,
                () ->
                        kamerDtoToKamer.convert(kamerDto),
                "to throw error"
        );

        kamerDto.setNaam("kk");

        assertThrows(KamerNaamLengteIsTeKlein.class,
                () ->
                        kamerDtoToKamer.convert(kamerDto),
                "to throw error"
        );

        kamerDto.setNaam("bigRoom");

        kamerDto.setSluit(LocalDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(7, 0)));

        assertThrows(KamerEindDatumIsVoorHuidigeTijd.class,
                () ->
                        kamerDtoToKamer.convert(kamerDto),
                "to throw error"
        );


        kamerDto.setSluit(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1)));
        kamerDto.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2)));
        System.out.println(kamerDto);
        assertThrows(EindTijdIsBeforeStartTijd.class,
                () ->
                        kamerDtoToKamer.convert(kamerDto),
                "to throw error"
        );

        LocalTime loccalstarttime = LocalTime.now().plusHours(1);
        LocalTime localSluitTime = LocalTime.now().plusHours(2);

        kamerDto.setNaam("standaard-kamer-naam");
        kamerDto.setStart(LocalDateTime.of(LocalDate.now(), loccalstarttime));
        kamerDto.setSluit(LocalDateTime.of(LocalDate.now(), localSluitTime));
        Kamer convert = kamerDtoToKamer.convert(kamerDto);

        assert convert != null;
        assertEquals("standaard-kamer-naam", convert.getNaam());
        assertEquals(loccalstarttime, convert.getStartTijd().toLocalTime());
        assertEquals(localSluitTime, convert.getSluitTijd().toLocalTime());
    }

    @Test
    void maakNieuweKamerAan() throws AanmakenVanKamerGingFout, KamerNaamLengteIsTeKlein, KamerBestaatAl, KamerNaamNotFoundException {
        // given

        KamerDto kamerDto = new KamerDto();


        kamerDto.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        kamerDto.setSluit(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        kamerDto.setNaam("kamer2");

        when(kamerRepo.findByNaam("kamer2")).thenReturn(kamerTestHelper.krijgKamers().get(0));

        assertThrows(KamerBestaatAl.class,
                () ->
                        kamerServiceImpl.maakNieuweKamerAan(kamerDto),
                "to throw error"
        );


        when(kamerRepo.findByNaam("")).thenReturn(null);

        kamerDto.setNaam("");

        when(kamerDtoToKamer.convert(kamerDto)).thenReturn(null);
        assertThrows(AanmakenVanKamerGingFout.class,
                () ->
                        kamerServiceImpl.maakNieuweKamerAan(kamerDto),
                "to throw error"
        );

        LocalTime loccalstarttime = LocalTime.now().plusHours(1);
        LocalTime localSluitTime = LocalTime.now().plusHours(2);
        LocalDate now = LocalDate.now();
        kamerDto.setNaam("standaard-kamer-naam");
        when(kamerRepo.findByNaam("standaard-kamer-naam")).thenReturn(null);
        kamerDto.setStart(LocalDateTime.of(now, loccalstarttime));
        kamerDto.setSluit(LocalDateTime.of(now, localSluitTime));
        KamerDtoToKamer kamerDtoToKamer2 = new KamerDtoToKamer();
        Kamer convert = kamerDtoToKamer2.convert(kamerDto);
        when(kamerDtoToKamer.convert(kamerDto)).thenReturn(convert);
        System.out.println(convert);
        kamerServiceImpl.maakNieuweKamerAan(kamerDto);
        ArgumentCaptor<Kamer> argument = ArgumentCaptor.forClass(Kamer.class);

        verify(kamerRepo, times(1)).save(convert);

    }


    @Test
    void editKamer() throws KamerIsNietGevonden, KamerBestaatAl, KamerNaamNotFoundException {
        KamerDto kamerDto = new KamerDto();


        kamerDto.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        kamerDto.setSluit(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        kamerDto.setNaam("kamer2");


        assertThrows(KamerNaamNotFoundException.class,
                () ->
                        kamerServiceImpl.editKamer(kamerDto, ""),
                "to throw error"
        );

        when(kamerRepo.findByNaam("bigRoomba")).thenReturn(null);

        assertThrows(KamerIsNietGevonden.class,
                () ->
                        kamerServiceImpl.editKamer(kamerDto, "bigRoomba"),
                "to throw error"
        );

        LocalTime loccalstarttime = LocalTime.now().plusHours(1);
        LocalTime localSluitTime = LocalTime.now().plusHours(2);
        LocalDate now = LocalDate.now();

        kamerDto.setNaam("standaard-kamer-naam");
        when(kamerRepo.findByNaam("standaard-kamer-naam")).thenReturn(kamerTestHelper.krijgKamers().get(0));
        kamerDto.setStart(LocalDateTime.of(now, loccalstarttime));
        kamerDto.setSluit(LocalDateTime.of(now, localSluitTime));
        KamerDtoToKamer kamerDtoToKamer2 = new KamerDtoToKamer();
        Kamer convert = kamerDtoToKamer2.convert(kamerDto);
        convert.setId(Long.valueOf(String.valueOf("1")));

        when(kamerDtoToKamer.convert(kamerDto)).thenReturn(convert);

            kamerServiceImpl.editKamer(kamerDto, "standaard-kamer-naam");


        verify(kamerRepo, times(1)).save(convert);
    }

    @Test
    void deleteKamerByNaam() throws KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException {
        when(kamerRepo.findByNaam("standaard-kamer-naam")).thenReturn(kamerTestHelper.krijgKamers().get(0));
        kamerServiceImpl.deleteKamerByNaam("standaard-kamer-naam");
        verify(kamerRepo, times(1)).delete(any());
    }

    @Test
    void reserveerKamer() throws EmailIsNietGevonden {
        User user = new User();
        when(userService.findUserByEmail("test@gmail.com")).thenReturn(user);
        when(kamerRepo.findByNaam("standaard-kamer-naam")).thenReturn(kamerTestHelper.krijgKamers().get(0));




    }


}
