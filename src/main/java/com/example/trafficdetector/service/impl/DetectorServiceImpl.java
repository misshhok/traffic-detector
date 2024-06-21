package com.example.trafficdetector.service.impl;

import com.example.trafficdetector.exceptions.BadRequestException;
import com.example.trafficdetector.model.dto.DetectorActivate;
import com.example.trafficdetector.model.dto.DetectorInitialize;
import com.example.trafficdetector.model.entity.Detector;
import com.example.trafficdetector.properties.DetectorProperties;
import com.example.trafficdetector.service.interfaces.DetectorService;
import com.example.trafficdetector.service.interfaces.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetectorServiceImpl implements DetectorService {

    private final Detector detector;
    private final DetectorProperties detectorProperties;
    private final ValidationService validationService;

    @Override
    public Detector getDetector() {
        return detector;
    }

    @Override
    public void initializeDetector(DetectorInitialize detectorInitialize) {
        if (!validationService.isValidSerialNumber(detectorInitialize.getSerialNumber()) ||
                !validationService.isValidCertificate(detectorInitialize.getConformityCertificate())) {
            log.error("Not valid request to initialize detector");
            throw new BadRequestException("Error in request parameters");
        }
        detector.initialize(detectorInitialize);
        saveStateInFile();
    }

    @Override
    public void activateDetector(DetectorActivate detectorActivate) {
        if (!validationService.isValidAddress(detectorActivate.getAddress()) ||
                !validationService.isValidZone(detectorActivate.getZone()) ||
                !validationService.isValidLocation(detectorActivate.getLocation())) {
            log.error("Not valid request to setup detector");
            throw new BadRequestException("Error in request parameters");
        }
        detector.activate(detectorActivate);
        saveStateInFile();
    }

    @Override
    public void setupDetector() {
        detector.setup();
        saveStateInFile();
    }

    @Override
    public void resetDetector() {
        detector.reset();
        saveStateInFile();
    }

    private void saveStateInFile() {
        Properties properties = new Properties();
        properties.setProperty("serialNumber", detector.getSerialNumber());
        properties.setProperty("model", detector.getModel());
        Optional.ofNullable(detector.getAddress()).ifPresent((property) -> properties.setProperty("address", property));
        properties.setProperty("state", detector.getState().toString());

        try (FileOutputStream outputStream = new FileOutputStream(detectorProperties.getPathToInfoFile())) {
            properties.store(outputStream, "Detector State");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}