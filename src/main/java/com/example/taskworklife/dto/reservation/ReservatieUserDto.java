package com.example.taskworklife.dto.reservation;

import java.time.LocalDateTime;


public interface ReservatieUserDto {
    LocalDateTime getEnd();

    public LocalDateTime getStart();

    public String getNaam();
}