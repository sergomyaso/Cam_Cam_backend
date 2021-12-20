package com.nsu.camcam.controller;

import com.nsu.camcam.controller.requests.ActivateModelRequest;
import com.nsu.camcam.controller.requests.CreateModelRequest;
import com.nsu.camcam.controller.requests.GetModelRequest;
import com.nsu.camcam.controller.response.GetFemaleModelResponse;
import com.nsu.camcam.controller.response.OperationResponse;
import com.nsu.camcam.model.Authority.Authority;
import com.nsu.camcam.model.Authority.Role;
import com.nsu.camcam.model.CamModel;
import com.nsu.camcam.model.UserCredential;
import com.nsu.camcam.service.CamModelService;
import com.nsu.camcam.service.CredentialsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@Log
public class CamModelController {
    private static final String MODEL_ALREADY_REGISTER = "Модель уже зарегистрирована, username:";
    private static final String MODEL_NOT_FOUND = "Такая модель не найдена ";

    @Autowired
    CamModelService femaleService;
    @Autowired
    CredentialsService credentialsService;

    @PostMapping("/models/create")
    public OperationResponse createModel(@RequestBody @Valid CreateModelRequest request) {
        UserCredential credential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CamModel foundedModel = femaleService.findModelByCredentials(credential);

        if (foundedModel != null) {
            return new OperationResponse(false, MODEL_ALREADY_REGISTER + credential.getUsername());
        }

        CamModel model = new CamModel(
                null,
                CamModel.FEMALE,
                request.getAge(),
                request.getGrowth(),
                request.getPenisSize(),
                request.getHairColor(),
                request.getTitiSize(),
                request.getPopaSize(),
                false,
                request.getPhotoUrl(),
                credential
        );
        femaleService.saveModel(model);

        return new OperationResponse(true, null);
    }

    @PostMapping("/models/update")
    public OperationResponse updateModel(@RequestBody @Valid CreateModelRequest request) {
        UserCredential credential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CamModel model = femaleService.findModelByCredentials(credential);

        if (model == null) {
            return new OperationResponse(false, MODEL_NOT_FOUND + credential.getUsername());
        }

        model.setAge(request.getAge());
        model.setGrowth(request.getGrowth());
        model.setHairColor(request.getHairColor());
        model.setPopaSize(request.getPopaSize());
        model.setTitiSize(request.getTitiSize());

        femaleService.saveModel(model);

        return new OperationResponse(true, null);
    }

    @PostMapping("/models/admin/active")
    public OperationResponse activateModel(@RequestBody @Valid ActivateModelRequest request) {
        UserCredential credential = credentialsService.findByUsername(request.getUsername());

        if (credential == null) {
            return new OperationResponse(false, MODEL_NOT_FOUND + request.getUsername());
        }

        CamModel model = femaleService.findModelByCredentials(credential);

        if (model == null) {
            return new OperationResponse(false, MODEL_NOT_FOUND + credential.getUsername());
        }

        boolean active = request.isActive();

        if (!active) {
            model.getCredential().setAuthorities(Collections.singleton(new Authority(Role.ROLE_USER)));
            model.setActive(false);

            return new OperationResponse(true, null);
        }

        model.setActive(request.isActive());
        model.getCredential().addAuthorities(new Authority(Role.ROLE_MODEL));

        return new OperationResponse(true, null);

    }

    @PostMapping("/get/models")
    public GetFemaleModelResponse getFemaleModel(@RequestBody @Valid GetModelRequest request) {
        UserCredential credential = credentialsService.findByUsername(request.getUsername());

        if (credential == null) {
            return null;
        }

        return new GetFemaleModelResponse(femaleService.findModelByCredentials(credential));
    }

}
