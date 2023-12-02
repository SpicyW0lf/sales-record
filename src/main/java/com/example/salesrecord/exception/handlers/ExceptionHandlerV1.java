package com.example.salesrecord.exception.handlers;

import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.exception.AlreadyExistsException;
import com.example.salesrecord.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice(basePackages = {"com.example.salesrecord"})
public class ExceptionHandlerV1 {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ResponseDto> alreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.ok(new ResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> notFoundError() {
        return ResponseEntity
                .status(404)
                .body(new ResponseDto("Not found in database"));
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ResponseDto> badRequest(HttpClientErrorException.BadRequest ex) {
        return ResponseEntity.badRequest()
                .body(new ResponseDto(ex.getMessage()));
    }
}
