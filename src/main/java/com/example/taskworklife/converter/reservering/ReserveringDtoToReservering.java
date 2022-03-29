package com.example.taskworklife.converter.reservering;


import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieDto;
import com.example.taskworklife.exception.kamer.EindTijdIsBeforeStartTijd;
import com.example.taskworklife.exception.kamer.EindDatumIsVoorHuidigeTijd;
import com.example.taskworklife.models.Reservering;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReserveringDtoToReservering implements Converter<MaakReservatieDto, Reservering> {


    @SneakyThrows
    public Reservering convert(MaakReservatieDto source) {
//        Reservering reservering = new Reservering();
//        //eindtijd is voor start tijd
        Reservering reservering = new Reservering();
        if (source.getEnd().isBefore(LocalDateTime.now()) || source.getStart().isBefore(LocalDateTime.now())) {
            throw new EindDatumIsVoorHuidigeTijd("De kamer eind tijd is al geweest");
        }
        if (source.getStart().isAfter(source.getEnd())) {
            throw new EindTijdIsBeforeStartTijd("De eindtijd is before start tijd");
        }
        reservering.setStart(source.getStart());
        reservering.setEnd(source.getEnd());
        return reservering;
        //        reservering.setEnd(source.getEindTijd());
//        reservering.setStart(source.getStartTijd());
//        return reservering;
    }
}
