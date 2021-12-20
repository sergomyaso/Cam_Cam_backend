package com.nsu.camcam.repository;

import com.nsu.camcam.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<UserCredential, Long> {
    UserCredential findByUsername(String username);
    UserCredential findUserCredentialById(Long id);
}
