package com.example.taskworklife.dto.reservation;

import java.time.LocalDateTime;


public interface ReservatieUserDto {
    String getId();

    LocalDateTime getEnd();

    public LocalDateTime getStart();

    public String getNaam();
}