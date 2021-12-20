package com.nsu.camcam.service;

import com.nsu.camcam.model.CamModel;
import com.nsu.camcam.model.UserCredential;
import com.nsu.camcam.repository.CamModelRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class CamModelService {
    @Autowired
    private CamModelRepository modelRepository;

    public void saveModel(CamModel model) {
        CamModel savedModel = modelRepository.save(model);
        if (savedModel != null) {
            log.info("Saved female model:" + savedModel.getCredential().getUsername());

            return;
        }

        log.severe("error to save female model" + model.getCredential().getUsername());
    }

    public CamModel findModelByCredentials(UserCredential credential) {
        return modelRepository.findByCredential(credential);
    }
}
