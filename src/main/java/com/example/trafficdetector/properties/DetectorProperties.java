package com.example.trafficdetector.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("detector")
public class DetectorProperties {

    private String pathToInfoFile;
}