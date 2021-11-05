package com.example.taskworklife.converter;


import com.example.taskworklife.dto.user.ReservatieDto;
import com.example.taskworklife.exception.kamer.EindTijdIsBeforeStartTijd;
import com.example.taskworklife.models.Reservering;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReserveringDtoToReservering implements Converter<ReservatieDto, Reservering> {


    @SneakyThrows
    @Override
    public Reservering convert(ReservatieDto source) {
        Reservering reservering = new Reservering();
        //eindtijd is voor start tijd
        if (source.getEindTijd().isBefore(source.getStartTijd())){
            throw new EindTijdIsBeforeStartTijd("Eind tijd is voor start tijd");
        }
        reservering.setEnd(source.getEindTijd());
        reservering.setStart(source.getStartTijd());
        return reservering;
    }
}
