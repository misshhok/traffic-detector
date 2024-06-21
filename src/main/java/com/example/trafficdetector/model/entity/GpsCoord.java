package com.example.trafficdetector.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GpsCoord {

    private double latitude;
    private double longitude;
}