package com.example.taskworklife.controller;

import com.example.taskworklife.exception.GlobalExceptionHandler;
import com.example.taskworklife.exception.global.ChangeOnlyOwnUserException;
import com.example.taskworklife.exception.global.IoException;
import com.example.taskworklife.exception.images.ImageNotFoundException;
import com.example.taskworklife.exception.images.FotoTypeIsNietToegestaan;
import com.example.taskworklife.exception.images.ImagesExceededLimit;
import com.example.taskworklife.exception.images.ImagesNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNaamIsLeegException;
import com.example.taskworklife.exception.kamer.KamerNaamLengteIsTeKlein;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.exception.user.EmailIsNietGevonden;
import com.example.taskworklife.models.user.UserPrincipal;
import com.example.taskworklife.service.file.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping(path = "/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImagesController extends GlobalExceptionHandler {
    ImagesService imagesService;

    @Autowired
    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping(value = "/kamer/{naam}/upload/images", consumes = {"multipart/mixed", "multipart/form-data"})
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> handelKamerFoto(@RequestPart("files") MultipartFile[] files, @PathVariable String naam) throws KamerIsNietGevonden, FotoTypeIsNietToegestaan, ImagesExceededLimit, ImagesNotFoundException, IOException, KamerNaamIsLeegException, KamerNaamNotFoundException, IoException, KamerNaamLengteIsTeKlein {
        return new ResponseEntity<Boolean>(imagesService.saveKamerImage(naam, files),HttpStatus.OK);
    }

    @PostMapping(value = "/user/{email}/upload", consumes = {"multipart/mixed", "multipart/form-data"})
    @CrossOrigin(origins = "http://localhost:3000")
    public  ResponseEntity<Boolean> handleProfielFoto(@RequestPart("file") MultipartFile file, Principal principal, @PathVariable("email") String email) throws EmailIsNietGevonden, FotoTypeIsNietToegestaan, IoException, IOException, ImageNotFoundException, ChangeOnlyOwnUserException {

        return new  ResponseEntity<Boolean>(imagesService.saveProfileImageUser(((UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser().getEmail(), email,file), HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{email}/image/delete")
    @CrossOrigin(origins = "http://localhost:3000")
    public void verwijderProfielFoto(@PathVariable("email") String email, Principal principal) throws EmailIsNietGevonden, ChangeOnlyOwnUserException, ImageNotFoundException {
        imagesService.deleteProfileImageUser(((UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser().getEmail(), email);
    }

}
