package com.example.taskworklife.service.reservaties;

import com.example.taskworklife.converter.reservering.ReserveringDtoToReservering;
import com.example.taskworklife.dto.reservation.AdminReservatieDto;
import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieUserDto;
import com.example.taskworklife.exception.reservatie.ReservatieNietGevondenException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.repo.KamerRepo;
import com.example.taskworklife.repo.ReservationRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservatiesImpl implements ReservatiesService {
    ReservationRepo reservationRepo;
    private final KamerRepo kamerRepo;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    ReserveringDtoToReservering reserveringDtoToReservering;

    @Autowired
    public ReservatiesImpl(ReservationRepo reservationRepo, KamerRepo kamerRepo) {
        this.reservationRepo = reservationRepo;
        this.kamerRepo = kamerRepo;
    }

    @Override
    public Page<ReservatieUserDto> getAllReservatiesByUser(UserPrincipal userPrincipal, String email, Integer pageSize, Integer pageNo) {
        Long id = userPrincipal.getUser().getId();
        PageRequest of = PageRequest.of(pageNo, pageSize);
        return reservationRepo.findAllReservatiesForUser(String.valueOf(id), of);
    }

    @Override
    public Page<AdminReservatieDto> getAllReservaties(Integer pageSize, Integer pageNo) {
        PageRequest of = PageRequest.of(pageNo, pageSize);
        return reservationRepo.findAllReservaties(of);
    }

    @Override
    public void deleteReservatie(Long id) throws ReservatieNietGevondenException {
        Optional<Reservering> reservering = reservationRepo.findById(id);
        if (reservering.isPresent()) {
            LOGGER.info("Reservatie verwijderd met naam " + id);
            Kamer kamer = reservering.get().getKamer();
            List<Reservering> reserveringStream = reservering.get().getKamer().getReservering().stream().filter(item -> !Objects.equals(item.getId(), id)).collect(Collectors.toList());
            kamer.setReservering(reserveringStream);
            kamerRepo.save(kamer);
            reservationRepo.deleteById(id);
        } else {
            throw new ReservatieNietGevondenException("De reservatie is niet gevonden");
        }
    }

    @Override
    public void editReservatie(Long id, MaakReservatieDto maakReservatieDto) throws ReservatieNietGevondenException {
        Reservering de_reservatie_is_niet_gevonden = reservationRepo.findById(id).orElseThrow(() ->
                new ReservatieNietGevondenException("De reservatie is niet gevonden"));
        Reservering convert = reserveringDtoToReservering.convert(maakReservatieDto);
        convert.setId(id);
        convert.setKamer(de_reservatie_is_niet_gevonden.getKamer());
        convert.setUser(de_reservatie_is_niet_gevonden.getUser());
        reservationRepo.save(convert);
    }
}
//    val arrItems : Mutable<String> = listOf()
//        for (findAllUserPermission in userRolePermissionRepository.findAllUserPermissions()) {
//        if(!arrItems.contains(findAllUserPermission.roleUserId)){
//        arrItems.
//        }
//        }