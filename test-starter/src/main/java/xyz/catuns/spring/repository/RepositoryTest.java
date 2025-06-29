package xyz.catuns.spring.repository;

import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("test")
@DataJpaTest(properties = {
        "spring.banner.mode=off"
})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public abstract class RepositoryTest<T extends JpaRepository<?,?>> {

    protected static final Logger log = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    protected T repository;

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
    }

    protected void printAll() {
        this.repository.findAll().stream()
                .map(Object::toString)
                .forEach(log::info);
    }
}
