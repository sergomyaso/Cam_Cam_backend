package com.nsu.camcam.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivateModelRequest {
    private boolean isActive;
    private String username;
}
