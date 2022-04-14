package com.example.taskworklife.exception;

import com.example.taskworklife.exception.global.ChangeOnlyOwnUserException;
import com.example.taskworklife.exception.global.ImageException;
import com.example.taskworklife.exception.global.IoException;
import com.example.taskworklife.exception.images.FotoTypeIsNietToegestaan;
import com.example.taskworklife.exception.images.ImagesExceededLimit;
import com.example.taskworklife.exception.images.ImagesNotFoundException;
import com.example.taskworklife.exception.reservatie.ReservatieNietGevondenException;
import com.example.taskworklife.models.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler extends CreateResponse {

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<HttpResponse> imageException(ImageException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(FotoTypeIsNietToegestaan.class)
    public ResponseEntity<HttpResponse> imageTypeNotAllowedException(FotoTypeIsNietToegestaan exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ImagesExceededLimit.class)
    public ResponseEntity<HttpResponse> ImagesExceededLimit(ImagesExceededLimit exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(ImagesNotFoundException.class)
    public ResponseEntity<HttpResponse> imagesNotFoundException(ImagesNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IoException.class)
    public ResponseEntity<HttpResponse> ioException(IoException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(ChangeOnlyOwnUserException.class)
    public ResponseEntity<HttpResponse> changeOnlyOwnUserException(ChangeOnlyOwnUserException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(ReservatieNietGevondenException.class)
    public ResponseEntity<HttpResponse> reservatieNietGevonden(ReservatieNietGevondenException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

}
