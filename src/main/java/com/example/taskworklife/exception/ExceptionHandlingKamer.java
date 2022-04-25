package com.example.taskworklife.exception;

import com.example.taskworklife.exception.kamer.*;
import com.example.taskworklife.models.HttpResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlingKamer extends CreateResponse implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }


    @ExceptionHandler(KamerIsNietGevonden.class)
    public ResponseEntity<HttpResponse> kamerNotFoundException(KamerIsNietGevonden exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(KamerBestaatAl.class)
    public ResponseEntity<HttpResponse> kamerAlreadyExist(KamerBestaatAl exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(KamerNaamNotFoundException.class)
    public ResponseEntity<HttpResponse> kamerNaamNotFoundException(KamerNaamNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(KamerNaamIsLeegException.class)
    public ResponseEntity<HttpResponse> kamerNaamIsLeegException(KamerNaamIsLeegException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(KamerReserveringBestaat.class)
    public ResponseEntity<HttpResponse> kamerReserveringBestaat(KamerReserveringBestaat exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(KamerReservatieMinimaalEenUur.class)
    public ResponseEntity<HttpResponse> kamerReservatieMinimaalEenUur(KamerReservatieMinimaalEenUur exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(KamerNaamLengteIsTeKlein.class)
    public ResponseEntity<HttpResponse> kamerNaamLengteIsTeKlein(KamerNaamLengteIsTeKlein exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AanmakenVanKamerGingFout.class)
    public ResponseEntity<HttpResponse> aanmakenVanDeKamerGingFout(AanmakenVanKamerGingFout aanmakenVanKamerGingFout){
        return createHttpResponse(BAD_REQUEST, aanmakenVanKamerGingFout.getMessage());
    }

    @ExceptionHandler(ReserveringNietOpZelfdeDagException.class)
    public ResponseEntity<HttpResponse> reserveringNietOpZelfdeDag(ReserveringNietOpZelfdeDagException reserveringNietOpZelfdeDagException){
        return createHttpResponse(BAD_REQUEST, reserveringNietOpZelfdeDagException.getMessage());
    }

}
