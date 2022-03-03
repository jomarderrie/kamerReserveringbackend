package com.example.taskworklife.exception.kamer;

import com.example.taskworklife.models.HttpResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class KamerExceptionHandling implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }

    @ExceptionHandler(KamerIsNietGevonden.class)
    public ResponseEntity<HttpResponse> KamerNotFoundException(KamerIsNietGevonden exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
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




    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

}
