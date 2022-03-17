package com.example.taskworklife.dto.reservation;

import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaakReservatieDto {

    private LocalDateTime end;

    private LocalDateTime start;

}
