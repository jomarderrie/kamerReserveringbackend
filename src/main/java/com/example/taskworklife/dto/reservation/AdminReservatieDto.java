package com.example.taskworklife.dto.reservation;


import java.time.LocalDateTime;

public interface AdminReservatieDto {
     String getId();

     LocalDateTime getEnd();

     LocalDateTime getStart();

     String getEmail();

     String getNaam() ;


     LocalDateTime getSluitTijd() ;


     LocalDateTime getStartTijd() ;

}
