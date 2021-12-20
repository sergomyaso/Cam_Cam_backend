package com.nsu.camcam.model.Authority;

public enum Role {
    ROLE_USER(1L),
    ROLE_ADMIN(2L),
    ROLE_MODEL(3L);

    private Long id;
    private static final String SPRING_PREFIX = "ROLE_";

    Role(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.name().replace(SPRING_PREFIX, "");
    }

    public Long getId() {
        return id;
    }
}