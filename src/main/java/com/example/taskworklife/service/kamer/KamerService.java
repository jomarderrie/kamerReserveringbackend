package com.example.taskworklife.service.kamer;


import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.dto.reservation.MaakReservatieDto;
import com.example.taskworklife.dto.reservation.ReservatieDto;
import com.example.taskworklife.exception.kamer.*;
import com.example.taskworklife.exception.user.EmailIsNietGevonden;
import com.example.taskworklife.models.Kamer;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;

public interface KamerService {
    Page<Kamer> getKamers(int paginaNummer, int paginaGroote, String sortBy);

    Kamer getKamerByNaam(String naam) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein;

    void maakNieuweKamerAan(KamerDto kamerDto) throws KamerIsNietGevonden, KamerBestaatAl, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein, AanmakenVanKamerGingFout;

    void editKamer(KamerDto kamerDto, String vorigNaam) throws KamerIsNietGevonden, KamerBestaatAl, KamerNaamNotFoundException;

    void deleteKamerByNaam(String naam) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein;

    void reserveerKamer(String kamerNaam, MaakReservatieDto reservatieDto, String email) throws KamerNaamNotFoundException, KamerNaamIsLeegException, KamerIsNietGevonden, EindTijdIsBeforeStartTijd, KamerReserveringBestaat, EmailIsNietGevonden, KamerNaamLengteIsTeKlein;

    List<ReservatieDto> getAllKamerReservatiesOpEenBepaaldeDag(String naam, Date date) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein;
}
