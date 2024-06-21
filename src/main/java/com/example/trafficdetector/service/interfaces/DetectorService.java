package com.example.trafficdetector.service.interfaces;

import com.example.trafficdetector.model.dto.DetectorActivate;
import com.example.trafficdetector.model.dto.DetectorInitialize;
import com.example.trafficdetector.model.entity.Detector;

public interface DetectorService {

    Detector getDetector();

    void initializeDetector(DetectorInitialize detectorInitialize);

    void activateDetector(DetectorActivate detectorActivate);

    void setupDetector();

    void resetDetector();
}
