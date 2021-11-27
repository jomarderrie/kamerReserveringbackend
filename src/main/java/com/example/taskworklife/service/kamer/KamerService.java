package com.example.taskworklife.service.kamer;


import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.dto.user.ReservatieDto;
import com.example.taskworklife.exception.kamer.*;
import com.example.taskworklife.exception.user.EmailNotFoundException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public interface KamerService {
    List<Kamer> getKamers();

    Kamer getKamerByNaam(String naam) throws KamerNotFoundException, KamerNaamNotFoundException;

    void maakNieuweKamerAan(KamerDto kamerDto) throws KamerNotFoundException, KamerAlreadyExist, KamerNaamNotFoundException;

    void editKamer(KamerDto kamerDto, String vorigNaam) throws KamerNotFoundException, KamerAlreadyExist, KamerNaamNotFoundException;

    void deleteKamerByNaam(String naam) throws KamerNotFoundException, KamerNaamNotFoundException;

    void reserveerKamer(String kamerNaam, ReservatieDto reservatieDto, String email) throws KamerNaamNotFoundException, KamerNaamIsLeegException, KamerNotFoundException, EindTijdIsBeforeStartTijd, KamerReserveringBestaat, EmailNotFoundException;

    List<Object> getAllKamerReservationsOnCertainDay(String naam, Date date) throws KamerNotFoundException, KamerNaamNotFoundException;
}
