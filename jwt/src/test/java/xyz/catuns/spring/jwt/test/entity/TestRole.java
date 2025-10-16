package xyz.catuns.spring.jwt.test.entity;

import jakarta.persistence.Entity;
import xyz.catuns.spring.jwt.domain.entity.RoleEntity;

@Entity
public class TestRole extends RoleEntity {

    public TestRole() {
        super();
    }


    public TestRole(String name) {
        super();
        setName(name);
    }


}
