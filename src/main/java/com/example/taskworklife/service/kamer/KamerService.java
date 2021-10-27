package com.example.taskworklife.service.kamer;



import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.exception.kamer.KamerAlreadyExist;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.models.Kamer;

import java.util.List;

public interface KamerService {
    List<Kamer> getKamers();
    Kamer getKamerByNaam(String naam) throws KamerNotFoundException;
    void maakNieuweKamerAan(KamerDto kamerDto) throws KamerNotFoundException, KamerAlreadyExist;
    void editKamer(KamerDto kamerDto, String vorigNaam) throws KamerNotFoundException, KamerAlreadyExist, KamerNaamNotFoundException;
    void deleteKamerByNaam(String naam) throws KamerNotFoundException;

}
