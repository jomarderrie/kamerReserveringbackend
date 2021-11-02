package com.example.taskworklife.controller;

import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.exception.ExceptionHandlingKamer;
import com.example.taskworklife.exception.ExceptionHandlingUser;
import com.example.taskworklife.exception.kamer.KamerAlreadyExist;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.service.kamer.KamerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Kamer>> getKamers(){
        return new ResponseEntity<>(kamerService.getKamers(), HttpStatus.OK);
    }

    @GetMapping("/{kamerNaam}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Kamer> getKamerMetNaam(@PathVariable String kamerNaam) throws KamerNotFoundException {
        return new ResponseEntity<>(kamerService.getKamerByNaam(kamerNaam), HttpStatus.OK);
    }
    @PostMapping("/new")
    @CrossOrigin(origins = "http://localhosFt:3000")
    public void maakNieuweKamerAan(@RequestBody KamerDto kamerDto) throws KamerAlreadyExist, KamerNotFoundException {
        kamerService.maakNieuweKamerAan(kamerDto);
    }

    @PutMapping("/edit/{vorigeNaam}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void editKamer(@RequestBody KamerDto kamerDto, @PathVariable("vorigeNaam") String vorigeNaam ) throws KamerAlreadyExist, KamerNotFoundException, KamerNaamNotFoundException {
        kamerService.editKamer(kamerDto, vorigeNaam);
    }

    @DeleteMapping("/delete/{naam}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteKamer(@PathVariable("naam") String naam) throws KamerNotFoundException {
        kamerService.deleteKamerByNaam(naam);
    }


    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }


    @GetMapping("/hello")
    public String hello(){
        return "hello!";
    }
}
