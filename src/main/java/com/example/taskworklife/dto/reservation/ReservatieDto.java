package com.example.taskworklife.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservatieDto {

    private LocalDateTime end;

    private LocalDateTime start;

    private String naam;
    private String achterNaam;

}