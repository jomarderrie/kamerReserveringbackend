package com.example.taskworklife.controller;

import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.reservaties.ReservatiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/reservaties")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservatiesController {
        private ReservatiesService reservatiesService;

        @Autowired
        public ReservatiesController(ReservatiesService reservatiesService) {
                this.reservatiesService = reservatiesService;
        }

        @GetMapping("/alles")
        @CrossOrigin(origins = "http://localhost:3000")
        public void krijgAlleReservaties(){

        }

        @GetMapping("/{email}/alles")
        @CrossOrigin(origins = "http://localhost:3000")
        public void krijgAlleReservatiesVanEenGebruiker(@PathVariable String email) throws KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException {
                reservatiesService.getAllReservatiesByUser((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

}
