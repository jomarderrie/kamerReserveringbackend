package com.example.taskworklife.controller;

import com.example.taskworklife.exception.images.ImageTypeNotAllowedException;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.service.file.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Controller
@RequestMapping(path = "/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImagesController {
    ImagesService imagesService;

    @Autowired
    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping(value = "/kamer/{naam}/upload/images", consumes = {"multipart/mixed","multipart/form-data"})
    @CrossOrigin(origins = "http://localhost:3000")
    public void handelKamerImagePost(@RequestPart("files") MultipartFile[] files) throws KamerNotFoundException, ImageTypeNotAllowedException {
        System.out.println(Arrays.toString(files));
//        imagesService.saveKamerImage(naam, FormData);
    }

    @PostMapping(value = "/kamer/{naam}/upload/image", consumes = {"multipart/mixed","multipart/form-data"})
    @CrossOrigin(origins = "http://localhost:3000")
    public void handelKamerImagePost(@RequestParam("file") MultipartFile file) throws KamerNotFoundException, ImageTypeNotAllowedException {
        System.out.println(file);
//        imagesService.saveKamerImage(naam, FormData);
    }



}
