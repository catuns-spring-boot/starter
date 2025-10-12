package xyz.catuns.spring.jwt.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import xyz.catuns.spring.jwt.TestApplication;
import xyz.catuns.spring.jwt.test.entity.TestUser;
import xyz.catuns.spring.jwt.test.repository.TestUserRepository;
import xyz.catuns.spring.repository.RepositoryTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = TestApplication.class)
class UserEntityRepositoryTest extends RepositoryTest<TestUserRepository> {


    @Test
    @DisplayName("should create user")
    void saveNewUser() {
        /* GIVEN - test user */
        TestUser given = new TestUser();
        given.setEmail("test@email.com");
        given.setPassword("password");

        /* WHEN - save */
        var result = repository.save(given);

        /* THEN - new user */
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
    }

    @Test
    @DisplayName("should find user by email")
    void findByEmailShouldWork() {
        /* GIVEN - test user */
        TestUser given = createValidUser();
        given.setEmail("email");
        given = repository.save(given);

        /* WHEN - find by email */
        var result = repository.findByEmail("email");

        /* THEN - should work */
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(given.getId());
    }

    private TestUser createValidUser() {
        TestUser given = new TestUser();
        given.setEmail("test@email.com");
        given.setPassword("password");
        return given;
    }

    
}