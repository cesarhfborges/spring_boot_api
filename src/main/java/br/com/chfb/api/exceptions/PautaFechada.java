package br.com.chfb.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PautaFechada extends RuntimeException {
    public ResponseEntity<String> response() {
        return new ResponseEntity<>("Access Denied", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
