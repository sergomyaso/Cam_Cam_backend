package com.nsu.camcam.service;

import com.nsu.camcam.model.Authority.Authority;
import com.nsu.camcam.model.Authority.Role;
import com.nsu.camcam.model.UserCredential;
import com.nsu.camcam.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveCredentials(UserCredential userCredential) {
        UserCredential userCredentialFromDB = credentialsRepository.findByUsername(userCredential.getUsername());
        if (userCredentialFromDB != null)
            return false;

        userCredential.setAuthorities(Collections.singleton(new Authority(Role.ROLE_USER)));
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        credentialsRepository.save(userCredential);

        return true;
    }

    public void updateCredentials(String username, String password) {
        UserCredential user = credentialsRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        credentialsRepository.save(user);
    }

    public UserCredential findByUsername(String username) {
        return credentialsRepository.findByUsername(username);
    }

    public UserCredential findByUsernameAndPassword(String email, String password) {
        UserCredential userCredential = findByUsername(email);
        if (userCredential != null) {
            if (passwordEncoder.matches(password, userCredential.getPassword())) {
                return userCredential;
            }
        }

        return null;
    }


}