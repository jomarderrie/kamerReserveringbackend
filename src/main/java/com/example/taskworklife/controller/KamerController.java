package com.example.taskworklife.controller;

import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieKamerDto;
import com.example.taskworklife.exception.ExceptionHandlingKamer;
import com.example.taskworklife.exception.kamer.*;
import com.example.taskworklife.exception.reservatie.ReservatieNietGevondenException;
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

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @GetMapping("/searched")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<Kamer>> getGezochteKamer(
            @RequestParam(required = false) @NotBlank String searched,
            @RequestParam(defaultValue = "false", required = false) Boolean alGereserveerde,
            @RequestParam(defaultValue = "false", required = false) Boolean eigenReservaties, Principal principal,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize
            ) throws KamerNaamNotFoundException {
        return new ResponseEntity<Page<Kamer>>(kamerService.getKamerByNaamEnSortables(searched, alGereserveerde, eigenReservaties, (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), pageNo, pageSize), HttpStatus.OK);
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
    public ResponseEntity<List<ReservatieKamerDto>> getKamerMetEenBepaaldeNaamOpEenBepaaldeDatum(@PathVariable("kamerNaam") String kamerNaam, @PathVariable("datum") String datum) throws KamerNaamNotFoundException, KamerIsNietGevonden, ParseException, KamerNaamLengteIsTeKlein {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date sqlDate = new Date(formatter.parse(datum).getTime());
        System.out.println(sqlDate);
        return new ResponseEntity<List<ReservatieKamerDto>>(kamerService.getAllKamerReservatiesOpEenBepaaldeDag(kamerNaam, sqlDate), HttpStatus.OK);
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
    @PostMapping("/{naam}/reserveer/edit/{reservatieId}")
    private void editReservatie(){

    }

    @DeleteMapping("/{kamerNaam}/delete/reservatie/{id}")
    private void verwijderReservatieByIdByKamerNaam(@PathVariable String kamerNaam, @PathVariable Long id) throws ReservatieNietGevondenException, KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException {
        kamerService.deleteReservatieByKamerNaam(kamerNaam,id);
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
