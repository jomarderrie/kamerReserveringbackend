package com.example.taskworklife.repo;

import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.dto.reservation.TestDTO;
import com.example.taskworklife.models.Reservering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends PagingAndSortingRepository<Reservering, Long > {
    @Query(value = "SELECT r.end, r.start, k.naam from reservering r, User u, KAMER k where user_id = ?1 AND (r.user_id = u.id) and (k.ID = r.KAMER_ID) ORDER BY r.start asc",
            countQuery = "SELECT count(*) from kamer where user_id = ?1", nativeQuery = true)
    Page<TestDTO> findAllReservatiesForUser(String userId, Pageable pageable);

//    Page<Reservering> findAllReservaties();
}
