package com.example.trafficdetector.service.interfaces;

import com.example.trafficdetector.model.entity.ConformityCertificate;
import com.example.trafficdetector.model.entity.GpsCoord;
import com.example.trafficdetector.model.entity.Zone;

public interface ValidationService {

    boolean isValidSerialNumber(String serialNumber);

    boolean isValidCertificate(ConformityCertificate certificate);

    boolean isValidAddress(String address);

    boolean isValidLocation(GpsCoord location);

    boolean isValidZone(Zone zone);
}
