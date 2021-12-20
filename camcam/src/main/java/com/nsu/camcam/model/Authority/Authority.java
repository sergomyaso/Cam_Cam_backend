package com.nsu.camcam.model.Authority;

import com.nsu.camcam.model.UserCredential;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Authority")
@Data
public class Authority implements GrantedAuthority {
    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "authorities")
    private Set<UserCredential> userCredentials;

    public Authority() {
    }

    public Authority(Role role) {
        this.id = role.getId();
        this.name = role.name();
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}