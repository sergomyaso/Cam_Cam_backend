package com.nsu.camcam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamModel {
    public static final String FEMALE = "FEMALE";

    @Id
    @GeneratedValue
    private Long id;
    private String male;
    private int age;
    private int growth;
    private int penisSize;
    private String hairColor;
    private String titiSize;
    private String popaSize;
    private boolean isActive;
    private String photoUrl;
    @OneToOne
    UserCredential credential;
}
