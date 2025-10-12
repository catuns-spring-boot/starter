package xyz.catuns.spring.jwt.test.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import xyz.catuns.spring.jwt.domain.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class TestUser extends UserEntity {

    @OneToMany(cascade = {CascadeType.ALL})
    private final List<TestRole> roles = new ArrayList<>();

    @Override
    public Collection<TestRole> getRoles() {
        return roles;
    }
}