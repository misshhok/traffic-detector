package com.example.trafficdetector.service.impl;

import com.example.trafficdetector.model.entity.ConformityCertificate;
import com.example.trafficdetector.model.entity.GpsCoord;
import com.example.trafficdetector.model.entity.Point;
import com.example.trafficdetector.model.entity.Zone;
import com.example.trafficdetector.service.interfaces.ValidationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean isValidSerialNumber(String serialNumber) {
        return serialNumber != null && serialNumber.matches("^[a-zA-Z0-9-]{6,50}$");
    }

    @Override
    public boolean isValidCertificate(ConformityCertificate certificate) {
        if (certificate == null || certificate.getNumber() == null || certificate.getExpirationDate() == null) {
            return false;
        }
        return certificate.getNumber().length() <= 50 && isValidIso8601Date(certificate.getExpirationDate());
    }

    @Override
    public boolean isValidAddress(String address) {
        return address != null && address.length() <= 512;
    }

    @Override
    public boolean isValidLocation(GpsCoord location) {
        if (location == null) {
            return false;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return latitude >= -90.0 && latitude <= 90.0 && longitude >= -180.0 && longitude <= 180.0;
    }

    @Override
    public boolean isValidZone(Zone zone) {
        if (zone == null || zone.getLocation() == null || zone.getAddress() == null ||
                zone.getVrpDetectionArea() == null || zone.getVrpDetectionArea().length != 2) {
            return false;
        }
        double latitude = zone.getLocation().getLatitude();
        double longitude = zone.getLocation().getLongitude();
        if (latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude > 180.0) {
            return false;
        }
        for (Point point : zone.getVrpDetectionArea()) {
            if (point.getX() < 0 || point.getX() > 3840 || point.getY() < 0 || point.getY() > 2160) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidIso8601Date(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}