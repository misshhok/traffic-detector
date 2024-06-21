package com.example.trafficdetector.model.dto;

import com.example.trafficdetector.model.entity.ConformityCertificate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetectorInitialize {

    private String serialNumber;
    private String model;
    private ConformityCertificate conformityCertificate;
}