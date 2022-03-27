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
    @Query(value = "SELECT * from reservering where user_id = ?1",
            countQuery = "SELECT count(*) from kamer where user_id = ?1", nativeQuery = true)
    Page<Reservering> findAllReservatiesForUser(String userId, Pageable pageable);
}
