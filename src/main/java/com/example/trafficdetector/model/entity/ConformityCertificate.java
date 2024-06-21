package com.example.trafficdetector.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConformityCertificate {

    private String number;
    private String expirationDate;
}