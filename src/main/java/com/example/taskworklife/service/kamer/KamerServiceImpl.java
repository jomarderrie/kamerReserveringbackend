package com.example.taskworklife.service.kamer;

import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.repo.KamerRepo;
import com.example.taskworklife.service.kamer.KamerService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KamerServiceImpl implements KamerService {
    private final KamerRepo kamerRepo;

    public KamerServiceImpl(KamerRepo kamerRepo) {
        this.kamerRepo = kamerRepo;
    }

    @Override
    public List<Kamer> getKamers() {
        List<Kamer> kamerList = new ArrayList<>();
        kamerRepo.findAll().iterator().forEachRemaining(kamerList::add);
        return kamerList;
    }

    @Override
    public Kamer getKamerByNaam(String naam) throws KamerNotFoundException {
        Kamer kamerByNaam = kamerRepo.findByNaam(naam);
        if (kamerByNaam == null) {
            throw new KamerNotFoundException("Kamer niet gevonden");
        }

        return kamerByNaam;
    }


}
