package com.example.taskworklife.service.reservaties;

import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.repo.ReservationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReservatiesImpl implements ReservatiesService{
    ReservationRepo reservationRepo;

    @Autowired
    public ReservatiesImpl(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    @Override
    public List<Reservering> getAllReservatiesByUser(UserPrincipal userPrincipal) {
        Long id = userPrincipal.getUser().getId();
        PageRequest of = PageRequest.of(0, 5);

        Page<Reservering> allReservatiesForUser = reservationRepo.findAllReservatiesForUser(String.valueOf(id), of);

        System.out.println(allReservatiesForUser);


        return null;
    }
}
