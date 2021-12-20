package com.nsu.camcam.repository;

import com.nsu.camcam.model.CamModel;
import com.nsu.camcam.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamModelRepository extends JpaRepository<CamModel, Long> {
    CamModel findByCredential(UserCredential credential);
}
