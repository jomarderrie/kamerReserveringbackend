package com.example.taskworklife.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservatieKamerDto {

    private LocalDateTime end;

    private LocalDateTime start;

    private String naam;
    private String achterNaam;

}

