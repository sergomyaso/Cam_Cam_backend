package com.nsu.camcam.repository;

import com.nsu.camcam.model.Authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}