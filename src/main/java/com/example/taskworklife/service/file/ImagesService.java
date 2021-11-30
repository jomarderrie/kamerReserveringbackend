package com.example.taskworklife.service.file;

import com.example.taskworklife.exception.global.IoException;
import com.example.taskworklife.exception.images.ImageNotFoundException;
import com.example.taskworklife.exception.images.ImageTypeNotAllowedException;
import com.example.taskworklife.exception.images.ImagesExceededLimit;
import com.example.taskworklife.exception.images.ImagesNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNaamIsLeegException;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.exception.user.EmailNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagesService {
    boolean saveKamerImage(String kamerNaam, MultipartFile[] files) throws KamerNotFoundException, ImageTypeNotAllowedException, ImagesExceededLimit, ImagesNotFoundException, IOException, KamerNaamIsLeegException, KamerNaamNotFoundException;

    boolean saveProfileImageUser(String mail, MultipartFile profileImage) throws ImageNotFoundException, EmailNotFoundException, ImageTypeNotAllowedException, IOException, IoException;
}
