package com.example.trafficdetector.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Problem {

    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}