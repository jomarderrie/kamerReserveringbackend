package com.example.taskworklife.controller;

import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.service.file.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImagesController {
    ImagesService imagesService;

    @Autowired
    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping("/kamer/{naam}/upload/images")
    @CrossOrigin(origins = "http://localhost:3000")
    public void handelKamerImagePost(@PathVariable String naam,@RequestParam("files") MultipartFile[] files) throws KamerNotFoundException {
        imagesService.saveKamerImage(naam, files);
    }
}
