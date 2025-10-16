package xyz.catuns.spring.jwt.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.catuns.spring.jwt.test.entity.TestRole;
import xyz.catuns.spring.jwt.test.entity.TestUser;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {

    @Test
    @DisplayName("username should be email")
    void getUsernameIsEmail() {
        /* GIVEN - test user */
        TestUser given = new TestUser();
        given.setEmail("test");

        /* WHEN - get username */
        var result = given.getUsername();

        /* THEN - is email */
        assertThat(result).isEqualTo("test");
    }

    @Test
    @DisplayName("roles should be Granted Authorities")
    void getAuthoritiesShouldBeRoles() {
        /* GIVEN - test user */
        TestUser given = new TestUser();
        given.getRoles().add(new TestRole());

        /* WHEN - get authorities */
        var result = given.getAuthorities();

        /* THEN - should be roles */
        assertThat(result).isEqualTo(given.getRoles());
    }
}