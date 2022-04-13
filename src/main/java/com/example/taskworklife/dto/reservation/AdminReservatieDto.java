package com.example.taskworklife.dto.reservation;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public interface AdminReservatieDto {


     LocalDateTime getEnd();

     LocalDateTime getStart();

     String getEmail();

     String getNaam() ;


     LocalDateTime getSluitTijd() ;


     LocalDateTime getStartTijd() ;

}
