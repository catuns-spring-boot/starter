package xyz.catuns.spring.jwt.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import xyz.catuns.spring.jwt.TestApplication;
import xyz.catuns.spring.jwt.test.entity.TestUser;
import xyz.catuns.spring.jwt.test.repository.TestUserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = TestApplication.class)
class UserEntityRepositoryTest {

    @Autowired
    private TestUserRepository repository;

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