package com.example.taskworklife.kamer;

import com.example.taskworklife.dto.reservation.ReservatieKamerDto;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class KamerTestHelper {
    String standaardKamerNaam = "standaard-kamer-naam";
    String standaardPaginaSize = "10";
    String standaardPaginaNummer = "0";
    String standaardSort = "kamer";

    public List<Kamer> krijgKamers(){
        ArrayList<Kamer> kamers = new ArrayList<>();
        Kamer kamer2 = new Kamer();
        kamer2.setNaam("kamer2");
        kamer2.setStartTijd(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        kamer2.setSluitTijd(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        //kamer 1 reservering
        List<Reservering> reserveringListKamer2 = new ArrayList<>();

        Reservering reservering2Kamer1 = new Reservering();
        reservering2Kamer1.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));
        reservering2Kamer1.setEnd(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)));

        reserveringListKamer2.add(reservering2Kamer1);

        kamer2.setReservering(reserveringListKamer2);
        kamers.add(kamer2);
        return kamers;
    }


    public List<ReservatieKamerDto> krijgReservaties(){
        ArrayList<ReservatieKamerDto> reservatieDtos = new ArrayList<>();
        ReservatieKamerDto reservatieDto = new ReservatieKamerDto();
        reservatieDto.setStart(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        reservatieDto.setEnd(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        reservatieDto.setNaam("peter");
        reservatieDto.setAchterNaam("jan");
        reservatieDtos.add(reservatieDto);
        return reservatieDtos;
    }



}
