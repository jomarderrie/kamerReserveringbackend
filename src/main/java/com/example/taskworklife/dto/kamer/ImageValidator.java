package com.example.taskworklife.dto.kamer;

import com.example.taskworklife.exception.global.ImageException;
import com.example.taskworklife.fileservice.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Base64;

public class ImageValidator implements ConstraintValidator<Image, String> {
    @Autowired
    FileService fileService;


    public ImageValidator(FileService fileService) {
        this.fileService = fileService;
    }

    @SneakyThrows
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        String fileType = fileService.detectType(decodedBytes);
        if(fileType.equalsIgnoreCase("image/png") || fileType.equalsIgnoreCase("image/jpeg")) {
            return true;
        }else{
            throw new ImageException("Image is niet juist");
        }
        return false;
    }
}
