package com.example.taskworklife.service.reservaties;

import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.Reservering;
import com.example.taskworklife.models.user.UserPrincipal;

import java.util.List;

public interface ReservatiesService {
    List<Reservering> getAllReservatiesByUser(UserPrincipal naam) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein;
}
