package it.sonosolobit.lab.minitwitter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Nessun tweet trovato")
public class TweetNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 123123423123234324L;

}
