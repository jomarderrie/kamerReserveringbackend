package com.example.taskworklife.controller;

import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieDto;
import com.example.taskworklife.exception.ExceptionHandlingKamer;
import com.example.taskworklife.exception.kamer.*;
import com.example.taskworklife.exception.user.EmailIsNietGevonden;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.service.kamer.KamerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(path = "/kamer")
@CrossOrigin(origins = "http://localhost:3000")
public class KamerController extends ExceptionHandlingKamer {

    private final KamerService kamerService;



    @Autowired
    public KamerController(KamerService kamerService) {
        this.kamerService = kamerService;
    }


    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<Kamer>> getKamers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "naam", required = false) String sortBy)
    {
        return new ResponseEntity<>(kamerService.getKamers(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{kamerNaam}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Kamer> getKamerMetNaam(@PathVariable @NotBlank String kamerNaam) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein {
        return new ResponseEntity<>(kamerService.getKamerByNaam(kamerNaam), HttpStatus.OK);
    }

    @PostMapping("/new")
    @CrossOrigin(origins = "http://localhost:3000")
    public void maakNieuweKamerAan(@RequestBody KamerDto kamerDto) throws KamerBestaatAl, KamerIsNietGevonden, IOException, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein, AanmakenVanKamerGingFout {
        kamerService.maakNieuweKamerAan(kamerDto);
    }


    @GetMapping("/{kamerNaam}/reserveringen/{datum}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<ReservatieDto>> getAllKamersMetEenBepaaldeNaamOpEenBepaaldeDatum(@PathVariable("kamerNaam") String kamerNaam, @PathVariable("datum") String datum) throws KamerNaamNotFoundException, KamerIsNietGevonden, ParseException, KamerNaamLengteIsTeKlein {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        LocalDate.parse()
        java.sql.Date sqlDate = new Date(formatter.parse(datum).getTime());
        System.out.println(sqlDate);
        return new ResponseEntity<List<ReservatieDto>>(kamerService.getAllKamerReservatiesOpEenBepaaldeDag(kamerNaam, sqlDate), HttpStatus.OK);
    }


    @PutMapping("/edit/{vorigeNaam}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void editKamer(@RequestBody KamerDto kamerDto, @PathVariable("vorigeNaam") String vorigeNaam) throws KamerBestaatAl, KamerIsNietGevonden, KamerNaamNotFoundException {
        kamerService.editKamer(kamerDto, vorigeNaam);
    }

    @SneakyThrows
    @DeleteMapping("/delete/{naam}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void verwijderKamer(@PathVariable("naam") String naam) throws KamerIsNietGevonden, KamerNaamNotFoundException {
        kamerService.deleteKamerByNaam(naam);
    }

    @PostMapping("/{naam}/reserveer")
    @CrossOrigin(origins = "http://localhost:3000")
    public void reserveerKamer(@PathVariable("naam") String kamerNaam, @RequestBody MaakReservatieDto reservatieDto, Principal principal) throws KamerReserveringBestaat, EindTijdIsBeforeStartTijd, KamerNaamIsLeegException, KamerNaamNotFoundException, KamerIsNietGevonden, EmailIsNietGevonden, KamerNaamLengteIsTeKlein {
        kamerService.reserveerKamer(kamerNaam, reservatieDto, ((UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser().getEmail());

    }


    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }


    @GetMapping("/hello")
    public String hello() {
        return "hello!";
    }
}
