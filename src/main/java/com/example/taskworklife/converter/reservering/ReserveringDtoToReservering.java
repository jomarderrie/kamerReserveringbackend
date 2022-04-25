package com.example.taskworklife.converter.reservering;


import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.exception.kamer.EindTijdIsBeforeStartTijd;
import com.example.taskworklife.exception.kamer.EindDatumIsVoorHuidigeTijd;
import com.example.taskworklife.exception.kamer.ReserveringNietOpZelfdeDagException;
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
        if (! source.getEnd().toLocalDate().isEqual(source.getStart().toLocalDate())) {
            throw new ReserveringNietOpZelfdeDagException("De Reservering moet op de zelfde dag zijn");
        }
        if (source.getEnd().isBefore(LocalDateTime.now()) || source.getStart().isBefore(LocalDateTime.now())) {
            throw new EindDatumIsVoorHuidigeTijd("De kamer eind tijd is al geweest");
        }
        if (source.getStart().isAfter(source.getEnd())) {
            throw new EindTijdIsBeforeStartTijd("De eindtijd is voor ded start tijd");
        }
        reservering.setStart(source.getStart());
        reservering.setEnd(source.getEnd());
        return reservering;
    }
}
