package com.example.taskworklife.repo;

import com.example.taskworklife.dto.reservation.AdminReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieUserDto;
import com.example.taskworklife.models.Reservering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends PagingAndSortingRepository<Reservering, Long > {
    @Query(value = "SELECT r.end, r.start, k.naam from reservering r, User u, KAMER k where user_id = ?1 AND (r.user_id = u.id) and (k.ID = r.KAMER_ID) ORDER BY r.start asc",
            countQuery = "SELECT count(*) from reservering r where r.user_id = ?1", nativeQuery = true)
    Page<ReservatieUserDto> findAllReservatiesForUser(String userId, Pageable pageable);

    @Query(value = "SELECT r.end, r.start, u.email, k.naam, k.SLUITTIJD, k.STARTTIJD from Reservering r, USER u, Kamer k",
            countQuery = "SELECT count(*) from RESERVERING r",
            nativeQuery = true)
    Page<AdminReservatieDto> findAllReservaties(Pageable pageable);
}
