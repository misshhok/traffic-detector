package com.example.trafficdetector.controllers;

import com.example.trafficdetector.model.dto.DetectorActivate;
import com.example.trafficdetector.model.dto.DetectorInitialize;
import com.example.trafficdetector.model.entity.Detector;
import com.example.trafficdetector.service.interfaces.DetectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RequestMapping("/api/detector")
@RequiredArgsConstructor
@RestController
public class DetectorController {
    private final DetectorService detectorService;

    @Operation(description = "Getting detector",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Server error", responseCode = "500")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Detector getDetector() {
        log.info("Request to get Detector");
        return detectorService.getDetector();
    }

    @Operation(description = "Initialize detector",
            responses = {
                    @ApiResponse(description = "Success initialization", responseCode = "204"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Server error", responseCode = "500")}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/initialized")
    public void initializeDetector(@RequestBody DetectorInitialize detectorInitialize) {
        detectorService.initializeDetector(detectorInitialize);
        log.info("Successful initialization");
    }

    @Operation(description = "Activate detector",
            responses = {
                    @ApiResponse(description = "Success activation", responseCode = "204"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Server error", responseCode = "500")}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/active")
    public void activateDetector(@RequestBody DetectorActivate detectorActivate) {
        detectorService.activateDetector(detectorActivate);
        log.info("Successful activation");
    }

    @Operation(description = "Setup detector",
            responses = {
                    @ApiResponse(description = "Success setup", responseCode = "204"),
                    @ApiResponse(description = "Server error", responseCode = "500")}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/setup")
    public void setupDetector() {
        detectorService.setupDetector();
        log.info("Successful setup");
    }

    @Operation(description = "Reset detector",
            responses = {
                    @ApiResponse(description = "Success reset", responseCode = "204"),
                    @ApiResponse(description = "Server error", responseCode = "500")}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/reset")
    public void resetDetector() {
        detectorService.resetDetector();
        log.info("Successful reset");
    }
}