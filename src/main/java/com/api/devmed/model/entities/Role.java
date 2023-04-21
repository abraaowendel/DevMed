package com.api.devmed.model.entities;

import com.api.devmed.model.enums.RoleName;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ROLE")
public class Role implements GrantedAuthority, Serializable {

    private static final long serialVersionUUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID roleId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName roleName;

    public Role() {
    }

    public Role(UUID roleId, RoleName roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return this.roleName.toString();
    }
    public UUID getRoleId() {
        return roleId;
    }
    public RoleName getRoleName() {
        return roleName;
    }

}
