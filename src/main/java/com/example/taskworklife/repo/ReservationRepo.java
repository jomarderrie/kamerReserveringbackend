package com.example.taskworklife.repo;

import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends PagingAndSortingRepository<Reservering, Long > {
//    @Query(value = "SELECT r from reservering r, KAMER k where user_id = ?1 and (k.ID = r.KAMER_ID)",
//            countQuery = "SELECT count(*) from kamer where user_id = ?1 AND (r.user_id = u.id)", nativeQuery = true)
//    Page<Reservering> findAllReservatiesForUser(String userId, Pageable pageable);
//

}
