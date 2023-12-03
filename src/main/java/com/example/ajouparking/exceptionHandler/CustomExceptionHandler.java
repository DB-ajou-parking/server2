package com.example.ajouparking.exceptionHandler;

import com.example.ajouparking.dto.CommonResponseDto;
import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.exceptionHandler.exceptions.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<CommonResponseDto<?>> apiException(CustomApiException e){
        return new ResponseEntity<>(new CommonResponseDto<>(e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<CommonResponseDto<?>> validationException(CustomValidationException e){
        return new ResponseEntity<>(new CommonResponseDto<>(e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }
}
