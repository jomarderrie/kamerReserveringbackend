package com.example.taskworklife.service.kamer;

import com.example.taskworklife.converter.KamerDtoToKamer;
import com.example.taskworklife.converter.KamerToKamerDto;
import com.example.taskworklife.converter.ReserveringDtoToReservering;
import com.example.taskworklife.dto.kamer.KamerDto;
import com.example.taskworklife.dto.reservation.ReservatieDto;
import com.example.taskworklife.exception.kamer.*;
import com.example.taskworklife.exception.user.EmailIsNietGevonden;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.repo.FileAttachmentRepo;
import com.example.taskworklife.repo.KamerRepo;


import com.example.taskworklife.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KamerServiceImpl implements KamerService {
    private final KamerRepo kamerRepo;
    KamerDtoToKamer kamerDtoToKamer;
    KamerToKamerDto kamerToKamerDto;
    ReserveringDtoToReservering reserveringDtoToReservering;
    UserService userService;
    FileAttachmentRepo fileAttachmentRepository;
    FileService fileService;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public KamerServiceImpl(KamerRepo kamerRepo, KamerDtoToKamer kamerDtoToKamer, KamerToKamerDto kamerToKamerDto, ReserveringDtoToReservering reserveringDtoToReservering, FileService fileService, FileAttachmentRepo fileAttachmentRepository, UserService userService) {
        this.kamerRepo = kamerRepo;
        this.kamerDtoToKamer = kamerDtoToKamer;
        this.kamerToKamerDto = kamerToKamerDto;
        this.reserveringDtoToReservering = reserveringDtoToReservering;
        this.fileService = fileService;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.userService = userService;
    }

    public KamerServiceImpl(KamerRepo kamerRepo) {
        this.kamerRepo = kamerRepo;
    }

    /**
     * @return Lijst of kamers, als er geen gevonden is, geef een lege lijst terug
     */
    public Page<Kamer> getKamers(int paginaNummer, int paginaGroote, String sortBy) {

        return kamerRepo.findAll(PageRequest.of(paginaNummer, paginaGroote,Sort.by(sortBy)));
    }


    /**
     * @param naam de kamer naam waarop gezocht moet worden
     * @param date de datum waarop gefiltered moet worden voor de gegeven kamer
     * @return Een lijst van kamerreserveringen
     * @throws KamerIsNietGevonden wordt gegooit wanneer er geen kamer gevonden wordt
     * @throws KamerNaamNotFoundException wordt
     */
    public List<ReservatieDto> getAllKamerReservatiesOpEenBepaaldeDag(String naam, Date date) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein {
        getKamerByNaam(naam);
        return kamerRepo.findByNaamAndGetAllRoomsOnASpecifiedDay(date, naam).get();
    }


    /**
     * @param naam de kamer naam waarop gezocht moet worden
     * @return gevonden kamer met de naam
     * @throws KamerIsNietGevonden wordt gegooit wanneer er geen kamer gevonden wordt
     * @throws KamerNaamNotFoundException
     */
    @Override
    public Kamer getKamerByNaam(String naam) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein {
        if (!StringUtils.isNotBlank(naam) || naam.equalsIgnoreCase("undefined")) {
            throw new KamerNaamNotFoundException("Naam is leeg");
        }
        if (naam.length()<3){
            throw new KamerNaamLengteIsTeKlein("De lengte waarop je de kamer opzoek is te klein");
        }
        Kamer kamerByNaam = kamerRepo.findByNaam(naam);
        if (kamerByNaam == null) {
            throw new KamerIsNietGevonden("Kamer niet gevonden");
        }
        return kamerByNaam;
    }

    @Override
    public void maakNieuweKamerAan(KamerDto kamerDto) throws KamerBestaatAl, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein, AanmakenVanKamerGingFout {
        Kamer kamerByNaam = kamerRepo.findByNaam(kamerDto.getNaam());
        if (kamerByNaam == null) {
            Kamer kamer = kamerDtoToKamer.convert(kamerDto);
            if (kamer != null) {
                LOGGER.info("Kamer toegevoegd met naam " + kamerDto.getNaam());
                kamerRepo.save(kamer);
            }else{
                throw new AanmakenVanKamerGingFout("De kamer aanmaken ging fout");
            }
        } else {
            throw new KamerBestaatAl("Kamer bestaat al");
        }
    }

    @Override
    public void editKamer(KamerDto kamerDto, String vorigNaam) throws KamerIsNietGevonden, KamerBestaatAl, KamerNaamNotFoundException {
        if (!StringUtils.isNotBlank(vorigNaam)) {
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
            throw new KamerIsNietGevonden("Kamer niet gevonden");
        }
    }

    @Override
    public void deleteKamerByNaam(String naam) throws KamerIsNietGevonden, KamerNaamNotFoundException, KamerNaamLengteIsTeKlein {
        Kamer kamerByNaam = getKamerByNaam(naam);
        LOGGER.info("Kamer verwijderd met naam " + naam);
        kamerRepo.delete(kamerByNaam);
    }

    @Override
    public void reserveerKamer(String kamerNaam, ReservatieDto reservatieDto, String email) throws KamerNaamNotFoundException, KamerNaamIsLeegException, KamerIsNietGevonden, EindTijdIsBeforeStartTijd, KamerReserveringBestaat, EmailIsNietGevonden, KamerNaamLengteIsTeKlein {
        //check of kamer bestaat
        User user = userService.findUserByEmail(email);
        Kamer kamerByNaam = getKamerByNaam(kamerNaam);
        //check of het niet een lege string is of null
        if (!StringUtils.isNotBlank(kamerNaam)) {
            throw new KamerNaamIsLeegException("Kamer naam is leeg");
        }
        //check voor overlap de reserveringlijst van de kamer.
//        Optional<List<Object>> byNaamAndGetAllReserveringenOnSpecifiedTimeInterval = kamerRepo.findByNaamAndGetAllReserveringenOnSpecifiedTimeInterval(kamerNaam, reservatieDto.getStartTijd(), reservatieDto.getEindTijd());
//        if (byNaamAndGetAllReserveringenOnSpecifiedTimeInterval.get().size()==0){
//            Reservering convertedReservatie = reserveringDtoToReservering.convert(reservatieDto);
//            if (convertedReservatie != null) {
//                convertedReservatie.setUser(user);
//                kamerByNaam.addReservering(convertedReservatie);
//            }
//        }else{
//            throw new KamerReserveringBestaat("Kamer reservering bestaat al voor interval " + reservatieDto.getStartTijd() + " " + reservatieDto.getEindTijd());
//        }

//        kamerByNaam.setReservering(reserveringList);
        LOGGER.info("reservatie toegevoegd aan kamer met naam: " + kamerNaam);
        kamerRepo.save(kamerByNaam);
    }


}
