package com.example.taskworklife.repo;


import com.example.taskworklife.dto.reservation.AdminReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieKamerDto;
import com.example.taskworklife.models.Kamer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface KamerRepo extends PagingAndSortingRepository<Kamer, Long> {
    Kamer findByNaam(String kamerNaam);

//        @Query(value="from Kamer t INNER JOIN Reservering ON Kamer.id = Reservering.kamer.id where :dateToFilter BETWEEN cast(Reservering.start as Date)as start AND cast(Reservering.END as Date)")
//        Kamer findByNaamAndGetAllReservering enOnSpeicifedDay(@Param("dateToFilter") String dateToFilter);

    @Query(value = "SELECT new com.example.taskworklife.dto.reservation.ReservatieKamerDto(p.end, p.start, u.naam, u.achternaam)  FROM Kamer b,Reservering p, User  u WHERE (b.id = p.kamer.id) AND (u.id = p.user.id) AND (b.naam = :naam AND(:date between cast(p.start as date)   AND cast(p.end as date))) ORDER BY p.start asc , p.end asc ")
    Optional<List<ReservatieKamerDto>> findByNaamAndGetAllRoomsOnASpecifiedDay(@Param("date") Date date, @Param("naam") String naam);

    @Query(value = "SELECT p.end as end , p.start as start FROM Kamer b, Reservering p WHERE (b.id = p.kamer.id) AND (b.naam = :naam  AND (:startTijd < p.end) AND (:eindTijd  > p.start))")
    List<Object> findByNaamAndGetAllReserveringenOnSpecifiedTimeInterval(@Param("naam") String naam, @Param("startTijd") LocalDateTime startLocalDateTime, @Param("eindTijd") LocalDateTime eindLocalDateTime);

//    @Query(value = "SELECT b FROM Kamer b, Reservering p WHERE (b.id = p.kamer.id) AND (b.naam = :naam )")
//  List<AdminReservatieDto>  findByNaamAndGetAllReserveringenById();

    @Query(value = "SELECT * from Kamer where naam like %?1%",
            countQuery = "SELECT count(*) from kamer where naam like %?1%", nativeQuery = true)
    Page<Kamer> findAllKamersBySearchedString(String naam, Pageable pageable);
//        @Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1",
//                countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
//                nativeQuery = true)
//        Page<User> findByLastname(String lastname, Pageable pageable);
}
