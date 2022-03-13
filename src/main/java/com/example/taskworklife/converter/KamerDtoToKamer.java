package com.example.taskworklife.converter;

import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.exception.kamer.EindTijdIsBeforeStartTijd;
import com.example.taskworklife.exception.kamer.KamerEindDatumIsVoorHuidigeTijd;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.models.Kamer;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class KamerDtoToKamer implements Converter<KamerDto, Kamer> {
    @SneakyThrows
    @Override
    @Synchronized
    @Nullable
    public Kamer  convert(KamerDto source) {
        Kamer kamer = new Kamer();
        if (!StringUtils.isNotBlank(source.getNaam())) {
            throw new KamerNaamNotFoundException("Geen kamer naam");
        }



        if (source.getNaam().length()<3){
            throw new KamerNaamLengteIsTeKlein("De kamer is te klein");
        }
        kamer.setNaam(source.getNaam());

        if (source.getSluit().isBefore(LocalDateTime.now()) || source.getStart().isBefore(LocalDateTime.now())){
            throw new KamerEindDatumIsVoorHuidigeTijd("De kamer eind tijd is al geweest");
        }
        if (source.getStart().isAfter(source.getSluit())) {
            throw new EindTijdIsBeforeStartTijd("De eindtijd is before start tijd");
        }


        kamer.setSluitTijd(source.getSluit());
        kamer.setStartTijd(source.getStart());
        return kamer;
    }
}
