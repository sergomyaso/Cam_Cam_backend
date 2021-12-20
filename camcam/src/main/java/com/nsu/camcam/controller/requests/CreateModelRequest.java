package com.nsu.camcam.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModelRequest {
    private String male;
    private int age;
    private int growth;
    private int penisSize;
    private String hairColor;
    private String titiSize;
    private String popaSize;
    private String photoUrl;
}
