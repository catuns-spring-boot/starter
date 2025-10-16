package xyz.catuns.spring.jwt.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.catuns.spring.jwt.test.entity.TestRole;

import static org.assertj.core.api.Assertions.assertThat;
class RoleEntityTest {

    @Test
    @DisplayName("should set name")
    void setNameTestRoleShouldCreateRole() {
        /* GIVEN - test role */
        TestRole given = new TestRole();

        /* WHEN - set name */
        given.setName("test");

        /* THEN - should create role */
        assertThat(given.getName()).isEqualTo("TEST");
    }
  
}