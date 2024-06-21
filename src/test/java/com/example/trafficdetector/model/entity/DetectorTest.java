package com.example.trafficdetector.model.entity;

import com.example.trafficdetector.exceptions.BadRequestException;
import com.example.trafficdetector.model.dto.DetectorActivate;
import com.example.trafficdetector.model.dto.DetectorInitialize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.trafficdetector.enums.State.ACTIVE;
import static com.example.trafficdetector.enums.State.NEW;
import static com.example.trafficdetector.enums.State.SETUP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DetectorTest {

    private Detector detector;

    @BeforeEach
    void setUp() {
        detector = new Detector();
    }

    @Test
    void testInitialize() {
        DetectorInitialize initializeRequest = getDetectorInitialize();

        detector.initialize(initializeRequest);

        assertEquals(SETUP, detector.getState());
        assertEquals("001614-1", detector.getSerialNumber());
        assertEquals("Спектр-Д", detector.getModel());
        assertEquals(initializeRequest.getConformityCertificate(), detector.getConformityCertificate());
    }

    @Test
    void testInitializeShouldThrowException() {
        DetectorInitialize initializeRequest = getDetectorInitialize();
        detector.setState(ACTIVE);

        assertThrows(BadRequestException.class, () -> detector.initialize(initializeRequest));
    }

    @Test
    void testActivate() {
        DetectorInitialize initializeRequest = getDetectorInitialize();
        detector.initialize(initializeRequest);

        DetectorActivate activateRequest = getDetectorActivate();
        detector.activate(activateRequest);

        assertEquals(ACTIVE, detector.getState());
        assertEquals(activateRequest.getAddress(), detector.getAddress());
        assertEquals(activateRequest.getLocation(), detector.getLocation());
        assertEquals(activateRequest.getZone(), detector.getZone());
    }

    @Test
    void testActivateShouldThrowException() {
        detector.setState(ACTIVE);

        DetectorActivate detectorActivate = getDetectorActivate();

        assertThrows(BadRequestException.class, () -> detector.activate(detectorActivate));
    }

    @Test
    void testSetup() {
        DetectorInitialize initializeRequest = getDetectorInitialize();
        detector.initialize(initializeRequest);

        DetectorActivate activateRequest = getDetectorActivate();
        detector.activate(activateRequest);

        detector.setup();

        assertEquals(SETUP, detector.getState());
        assertNull(detector.getAddress());
        assertNull(detector.getLocation());
        assertNull(detector.getZone());
    }

    @Test
    void testSetupShouldThrowException() {
        detector.setState(NEW);

        assertThrows(BadRequestException.class, () -> detector.setup());
    }

    @Test
    void testReset() {
        DetectorInitialize initializeRequest = getDetectorInitialize();

        detector.initialize(initializeRequest);

        detector.reset();

        assertEquals(NEW, detector.getState());
        assertNull(detector.getSerialNumber());
        assertNull(detector.getModel());
        assertNull(detector.getAddress());
        assertNull(detector.getLocation());
        assertNull(detector.getZone());
        assertNull(detector.getConformityCertificate());
    }

    @Test
    void testResetShouldThrowException() {
        detector.setState(NEW);

        assertThrows(BadRequestException.class, () -> detector.reset());
    }

    private DetectorInitialize getDetectorInitialize() {
        DetectorInitialize initializeRequest = new DetectorInitialize();
        initializeRequest.setSerialNumber("001614-1");
        initializeRequest.setModel("Спектр-Д");
        initializeRequest.setConformityCertificate(new ConformityCertificate("74935", "2024-12-09"));
        return initializeRequest;
    }

    private DetectorActivate getDetectorActivate() {
        DetectorActivate activateRequest = new DetectorActivate();
        activateRequest.setAddress("Россия, Таврово, 2-й Магистральный мкр.");
        activateRequest.setLocation(new GpsCoord(50.506485, 36.608693));
        activateRequest.setZone(new Zone());
        return activateRequest;
    }
}