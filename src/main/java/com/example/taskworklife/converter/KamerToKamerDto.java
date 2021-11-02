package com.example.taskworklife.converter;

import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.models.Kamer;
import org.springframework.stereotype.Component;

import org.springframework.core.convert.converter.Converter;

@Component
public class KamerToKamerDto implements Converter<Kamer,KamerDto> {
    @Override
    public KamerDto convert(Kamer source) {

        return null;
    }
}
