package com.example.taskworklife.service.file;

import com.example.taskworklife.config.ReserveringConfiguration;
import com.example.taskworklife.exception.images.ImageTypeNotAllowedException;
import com.example.taskworklife.exception.images.ImagesExceededLimit;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.models.FileAttachment;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.repo.FileAttachmentRepo;
import com.example.taskworklife.service.kamer.KamerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class ImagesServiceImpl implements ImagesService {

    FileService fileService;
    KamerService kamerService;
    ReserveringConfiguration reserveringConfiguration;
    FileAttachmentRepo fileAttachmentRepository;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public ImagesServiceImpl(FileService fileService, KamerService kamerService, ReserveringConfiguration reserveringConfiguration, FileAttachmentRepo fileAttachmentRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileService = fileService;
        this.kamerService = kamerService;
        this.reserveringConfiguration = reserveringConfiguration;
    }

    @Override
    public void saveKamerImage(String kamerNaam, MultipartFile[] files) throws KamerNotFoundException, ImageTypeNotAllowedException, ImagesExceededLimit {
        //check if files are not null
        if (files != null) {
            //check if length is not 0
            if (files.length != 0) {
                //check if naam is not empty
                if (StringUtils.isNotBlank(kamerNaam)) {
                    if(files.length>8){
                        throw new ImagesExceededLimit("Niet meer dan 8 images");
                    }
                    //check if naam exits in db
                    Kamer kamerByNaam = kamerService.getKamerByNaam(kamerNaam);
                    Date date;
                    // detect if image
                    for (MultipartFile file : files) {
                        if(!fileService.detectIfImage(Objects.requireNonNull(file.getContentType()))){
                        throw new ImageTypeNotAllowedException("Image type is niet toegestaan alleen png, jpg, jpeg");
                        }
                    }

                    for (MultipartFile file : files) {
                        date = new Date();
                        FileAttachment fileAttachment = new FileAttachment();
                        fileAttachment.setDate(date);
                        try {
                            byte[] fileAsByte = file.getBytes();
//                            File target = new File
//                    (reserveringConfiguration.getKamerFolder() + "/"+ naam + "/"+ files[0].getOriginalFilename());
                            File target = new File(reserveringConfiguration.getKamerFolder() + "/" + kamerNaam + "/" + file.getOriginalFilename());
                            FileUtils.writeByteArrayToFile(target, fileAsByte);

                            fileAttachment.setFileType(file.getContentType());
                            fileAttachment.setName(file.getOriginalFilename());
                            LOGGER.info("Saved image with name " + file.getOriginalFilename() + " for room " + kamerNaam);
                            fileAttachmentRepository.save(fileAttachment);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
