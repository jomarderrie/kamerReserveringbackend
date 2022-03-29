package com.example.taskworklife.repo;

import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends PagingAndSortingRepository<Reservering, Long > {
    @Query(value = "SELECT * from reservering r, User u where user_id = ?1 AND WHERE(r.user_id = u.id)",
            countQuery = "SELECT count(*) from kamer where user_id = ?1 AND WHERE(r.user_id = u.id)", nativeQuery = true)
    Page<Reservering> findAllReservatiesForUser(String userId, Pageable pageable);
}
