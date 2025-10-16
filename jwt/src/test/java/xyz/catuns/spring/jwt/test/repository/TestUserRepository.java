package xyz.catuns.spring.jwt.test.repository;

import org.springframework.stereotype.Repository;
import xyz.catuns.spring.jwt.test.entity.TestUser;
import xyz.catuns.spring.jwt.domain.repository.UserEntityRepository;

@Repository
public interface TestUserRepository extends UserEntityRepository<TestUser> {
}
