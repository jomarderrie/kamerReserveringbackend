package com.example.taskworklife.controller;

import com.example.taskworklife.dto.reservation.AdminReservatieDto;
import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieUserDto;
import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.reservatie.ReservatieNietGevondenException;
import com.example.taskworklife.exception.user.EmailIsNietJuist;
import com.example.taskworklife.exception.user.GeenAdminException;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.service.reservaties.ReservatiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/reservaties")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservatiesController {
    private ReservatiesService reservatiesService;

    @Autowired
    public ReservatiesController(ReservatiesService reservatiesService) {
        this.reservatiesService = reservatiesService;
    }



    // TODO: 13/04/2022 implement sortby 
    @GetMapping("/{email}/alles")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<ReservatieUserDto>> krijgAlleReservatiesVanEenGebruiker(@PathVariable String email, @RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(defaultValue = "5", required = false) Integer pageSize) throws KamerIsNietGevonden, KamerNaamLengteIsTeKlein, KamerNaamNotFoundException, GeenAdminException, EmailIsNietJuist {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        checkOfEmailHetzelfdeIs(principal, email);
        return new ResponseEntity<Page<ReservatieUserDto>>(reservatiesService.getAllReservatiesByUser((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), email, pageSize, pageNo), HttpStatus.OK);
    }
    // TODO: 13/04/2022 implement sortby
    @GetMapping("/admin/alles")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<AdminReservatieDto>> krijgAlleReservaties(@RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(defaultValue = "5", required = false) Integer pageSize) throws GeenAdminException {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authCheck(principal);
       return new ResponseEntity<>(reservatiesService.getAllReservaties(pageSize, pageNo), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    private void verwijderReservatieById(@PathVariable Long id) throws ReservatieNietGevondenException {
        reservatiesService.deleteReservatie(id);
    }

    @PostMapping("/edit/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    private void veranderReservatieById(@PathVariable long id, @RequestBody MaakReservatieDto maakReservatieDto) throws ReservatieNietGevondenException {
        reservatiesService.editReservatie(id, maakReservatieDto);
    }
//    @PostMapping()
    private void authCheck(UserPrincipal principal) throws GeenAdminException {
        if (!principal.getUser().getRole().equals("ROLE_ADMIN")){
            throw new GeenAdminException("Geen admin role gevonden");
        }
    }


     private Boolean checkOfEmailHetzelfdeIs(UserPrincipal userPrincipal, String email) throws EmailIsNietJuist {
        if (Objects.equals(userPrincipal.getUser().getEmail(), email)){
            return true;
        }else {
            throw new EmailIsNietJuist("Je email klopt niet");
        }
     }
}
