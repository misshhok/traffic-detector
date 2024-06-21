package com.example.trafficdetector.model.entity;

import com.example.trafficdetector.enums.State;
import com.example.trafficdetector.exceptions.BadRequestException;
import com.example.trafficdetector.model.dto.DetectorActivate;
import com.example.trafficdetector.model.dto.DetectorInitialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.trafficdetector.enums.State.ACTIVE;
import static com.example.trafficdetector.enums.State.NEW;
import static com.example.trafficdetector.enums.State.SETUP;

@Slf4j
@Data
@Component
public class Detector {

    private State state = NEW;
    private String serialNumber;
    private String model;
    private String address;
    private GpsCoord location;
    private Zone zone;
    private ConformityCertificate conformityCertificate;

    public void initialize(DetectorInitialize detectorInitialize) {
        if (this.state != NEW) {
            log.error("State {} not valid for initialization", this.state);
            throw new BadRequestException("Detector must be in NEW state for initialization.");
        }

        this.serialNumber = detectorInitialize.getSerialNumber();
        this.model = detectorInitialize.getModel();
        this.conformityCertificate = detectorInitialize.getConformityCertificate();
        this.state = SETUP;
    }

    public void activate(DetectorActivate request) {
        if (this.state != SETUP) {
            log.error("State {} not valid for activation", this.state);
            throw new BadRequestException("Detector must be in SETUP state for activation.");
        }

        this.address = request.getAddress();
        this.location = request.getLocation();
        this.zone = request.getZone();
        this.state = ACTIVE;
    }

    public void setup() {
        if (this.state != ACTIVE) {
            log.error("State {} not valid for setup", this.state);
            throw new BadRequestException("Detector must be in NEW state for setup.");
        }

        this.state = SETUP;
        this.address = null;
        this.location = null;
        this.zone = null;
    }

    public void reset() {
        if (this.state != SETUP) {
            log.error("State {} not valid for reset", this.state);
            throw new BadRequestException("Detector must be in SETUP state for reset.");
        }

        this.serialNumber = null;
        this.model = null;
        this.address = null;
        this.location = null;
        this.zone = null;
        this.state = NEW;
        this.conformityCertificate = null;
    }
}