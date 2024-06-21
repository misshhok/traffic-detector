package com.example.trafficdetector.model.dto;

import com.example.trafficdetector.model.entity.GpsCoord;
import com.example.trafficdetector.model.entity.Zone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetectorActivate {

    private String address;
    private GpsCoord location;
    private Zone zone;
}