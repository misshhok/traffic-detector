package com.example.trafficdetector.controllers;

import com.example.trafficdetector.exceptions.BadRequestException;
import com.example.trafficdetector.model.dto.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Problem handleBadRequest(BadRequestException e) {
        return Problem.builder()
                .type("/problem/internal-server-error")
                .title("Bad request error")
                .status(400)
                .detail(e.getMessage())
                .instance("Some instance message")
                .build();
    }
}