package com.example.taskworklife.service.kamer;

import com.example.taskworklife.converter.KamerDtoToKamer;
import com.example.taskworklife.converter.KamerToKamerDto;
import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.exception.kamer.KamerAlreadyExist;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.repo.KamerRepo;
import com.example.taskworklife.service.kamer.KamerService;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KamerServiceImpl implements KamerService {
    private final KamerRepo kamerRepo;
    KamerDtoToKamer kamerDtoToKamer;
    KamerToKamerDto kamerToKamerDto;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public KamerServiceImpl(KamerRepo kamerRepo, KamerDtoToKamer kamerDtoToKamer, KamerToKamerDto kamerToKamerDto) {
        this.kamerRepo = kamerRepo;
        this.kamerDtoToKamer = kamerDtoToKamer;
        this.kamerToKamerDto = kamerToKamerDto;
    }


    @Override
    public List<Kamer> getKamers() {
        List<Kamer> kamerList = new ArrayList<>();
        for (Kamer kamer : kamerRepo.findAll()) {
            
        }
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

    @Override
    public void maakNieuweKamerAan(KamerDto kamerDto) throws KamerAlreadyExist {
        Kamer kamerByNaam = kamerRepo.findByNaam(kamerDto.getNaam());
        if (kamerByNaam == null) {
            Kamer kamer = kamerDtoToKamer.convert(kamerDto);
            if (kamer != null) {
                LOGGER.info("Kamer toegevoegd met naam " + kamerDto.getNaam());
                kamerRepo.save(kamer);
            }
        } else {
            throw new KamerAlreadyExist("Kamer bestaat al");
        }
    }

    @Override
    public void editKamer(KamerDto kamerDto, String vorigNaam) throws KamerNotFoundException, KamerAlreadyExist, KamerNaamNotFoundException {
        if (!StringUtils.isNotBlank(vorigNaam)){
            throw new KamerNaamNotFoundException("Vorige naam niet gevonden");
        }
        Kamer kamerByNaam = kamerRepo.findByNaam(vorigNaam);
        if (kamerByNaam != null) {
            Kamer kamer = kamerDtoToKamer.convert(kamerDto);
            if (kamer != null) {
                kamer.setId(kamerByNaam.getId());
                LOGGER.info("Kamer veranderd met naam " + kamerDto.getNaam());
                kamerRepo.save(kamer);
            }
        } else {
            throw new KamerNotFoundException("Kamer niet gevonden");
        }
    }

    @Override
    public void deleteKamerByNaam(String naam) throws KamerNotFoundException {
        Kamer kamerByNaam = getKamerByNaam(naam);
        LOGGER.info("Kamer verwijderd met naam " + naam);
        kamerRepo.delete(kamerByNaam);
    }
}
