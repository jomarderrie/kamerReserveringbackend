package com.example.taskworklife.service.reservaties;

import com.example.taskworklife.dto.reservation.AdminReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieUserDto;
import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.reservatie.ReservatieNietGevondenException;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.UserPrincipal;
import org.springframework.data.domain.Page;

public interface ReservatiesService {
    Page<ReservatieUserDto> getAllReservatiesByUser(UserPrincipal naam, String email, Integer pageSize, Integer pageNo) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein;

    Page<AdminReservatieDto> getAllReservaties(Integer pageSize, Integer pageNo);

    void deleteReservatie(Long id) throws ReservatieNietGevondenException;
}
