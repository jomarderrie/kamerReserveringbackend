package com.example.taskworklife.service.reservaties;

import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.repo.ReservationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservatiesImpl implements ReservatiesService{
    ReservationRepo reservationRepo;

    @Autowired
    public ReservatiesImpl(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    @Override
    public Page<Reservering> getAllReservatiesByUser(UserPrincipal userPrincipal, String email, Integer pageSize, Integer pageNo) {
        Long id = userPrincipal.getUser().getId();
        PageRequest of = PageRequest.of(pageNo, pageSize);
//        Page<Reservering> allReservatiesForUser = reservationRepo.findAllReservatiesForUser(String.valueOf(id), of);
//        System.out.println(allReservatiesForUser);
//        reservationRepo.findAllReservatiesForUser(String.valueOf(id), of);
        return null;
    }

    @Override
    public Page<Reservering> getAllReservaties(Integer pageSize, Integer pageNo, String sortBy) {
        return null;
    }


}
