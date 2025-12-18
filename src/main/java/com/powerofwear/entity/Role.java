package com.powerofwear.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import com.powerofwear.common.base.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class  Role extends BaseEntity implements GrantedAuthority {
    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
