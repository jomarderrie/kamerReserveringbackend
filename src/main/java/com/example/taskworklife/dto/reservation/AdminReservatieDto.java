package com.example.taskworklife.dto.reservation;


import java.time.LocalDateTime;

public interface AdminReservatieDto {
     Long getId();

     LocalDateTime getEnd();

     LocalDateTime getStart();

     String getEmail();

     String getNaam() ;


     LocalDateTime getSluitTijd() ;


     LocalDateTime getStartTijd() ;

}
