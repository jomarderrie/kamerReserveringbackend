package com.example.taskworklife.exception;

import com.example.taskworklife.exception.user.*;
import com.example.taskworklife.models.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlingUser extends CreateResponse  implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(EmailBestaatAl.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailBestaatAl exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @RequestMapping("/error")
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }

    @ExceptionHandler(TermenNietGeaccepteerd.class)
    public ResponseEntity<HttpResponse> termsNotAcceptedException(TermenNietGeaccepteerd exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailIsNietGevonden.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailIsNietGevonden exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NaamBestaatNiet.class)
    public ResponseEntity<HttpResponse> naamNotExistException(NaamBestaatNiet exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AchterNaamNotExistException.class)
    public ResponseEntity<HttpResponse> achterNaamNotExistException(AchterNaamNotExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NaamTeKleinException.class)
    public ResponseEntity<HttpResponse> naamTeKleinException(NaamTeKleinException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailIsNietJuist.class)
    public ResponseEntity<HttpResponse> emailNotValidException(EmailIsNietJuist exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(RegisterErrorException.class)
    public ResponseEntity<HttpResponse> registerErrorException(RegisterErrorException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(TokenParsingException.class)
    public ResponseEntity<HttpResponse> tokenParsingException(TokenParsingException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(TokenIsLeegException.class)
    public ResponseEntity<HttpResponse> tokenIsLeegException(TokenIsLeegException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<HttpResponse> tokenIsLeegException(LoginException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }




    @ExceptionHandler(GebruikerNietGevondenExcepion.class)
    public ResponseEntity<HttpResponse> gebruikerNietGevonden(GebruikerNietGevondenExcepion exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

}
