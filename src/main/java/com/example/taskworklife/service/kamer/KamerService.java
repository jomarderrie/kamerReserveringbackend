package com.example.taskworklife.service.kamer;



import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.models.Kamer;

import java.util.List;

public interface KamerService {
    List<Kamer> getKamers();
    Kamer getKamerByNaam(String naam) throws KamerNotFoundException;
}
