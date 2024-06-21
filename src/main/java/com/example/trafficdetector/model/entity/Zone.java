package com.example.trafficdetector.model.entity;

import lombok.Data;

@Data
public class Zone {

    private GpsCoord location;
    private String address;
    private Point[] vrpDetectionArea;
}