package com.example.taskworklife.controller;

import com.example.taskworklife.dto.reservation.ReservatieUserDto;
import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.user.GeenAdminException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.service.reservaties.ReservatiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void krijgAlleReservaties() throws GeenAdminException {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authCheck(principal);
//        return new ResponseEntity<>()
    }

    @GetMapping("/{email}/alles")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<ReservatieUserDto>> krijgAlleReservatiesVanEenGebruiker(@PathVariable String email, @RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(defaultValue = "10", required = false) Integer pageSize) throws KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException, GeenAdminException {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<Page<ReservatieUserDto>>(reservatiesService.getAllReservatiesByUser((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), email, pageSize, pageNo), HttpStatus.OK);
    }

    private void authCheck(UserPrincipal principal) throws GeenAdminException {
        if (!principal.getUser().getRole().equals("ROLE_ADMIN")){
            throw new GeenAdminException("Geen admin role gevonden");
        }
    }
}
