package com.example.taskworklife.exception;

import com.example.taskworklife.exception.global.ImageException;
import com.example.taskworklife.exception.user.EmailExistException;
import com.example.taskworklife.models.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler extends CreateResponse {

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<HttpResponse> imageException(ImageException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }



}
