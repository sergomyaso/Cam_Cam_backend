package com.nsu.camcam.controller.response;

import com.nsu.camcam.model.CamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFemaleModelResponse {
    private String male;
    private int age;
    private int growth;
    private int penisSize;
    private String hairColor;
    private String titiSize;
    private String popaSize;
    private String photoUrl;

    public GetFemaleModelResponse(CamModel model) {
        this.male = model.getMale();
        this.age = model.getAge();
        this.growth = model.getGrowth();
        this.hairColor = model.getHairColor();
        this.titiSize = model.getTitiSize();
        this.popaSize = model.getPopaSize();
        this.penisSize = model.getPenisSize();
    }
}
