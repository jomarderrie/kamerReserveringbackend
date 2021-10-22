package com.example.taskworklife.controller;

import com.example.taskworklife.exception.ExceptionHandlingUser;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.service.kamer.KamerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/kamer")
@CrossOrigin(origins = "http://localhost:3000")
public class KamerController extends ExceptionHandlingUser {

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



    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }


    @GetMapping("/hello")
    public String hello(){
        return "hello!";
    }
}
