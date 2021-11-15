package com.example.taskworklife.service.file;

import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
    void saveKamerImage(String kamerNaam, MultipartFile[] files) throws KamerNotFoundException;
}
