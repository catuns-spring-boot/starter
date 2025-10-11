package xyz.catuns.spring.jwt.domain.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.catuns.spring.jwt.exception.EmailNotFoundException;
import xyz.catuns.spring.jwt.domain.entity.UserEntity;
import xyz.catuns.spring.jwt.domain.repository.UserEntityRepository;

public class UserEntityService<E extends UserEntity> implements UserDetailsService {

    private final UserEntityRepository<E> userEntityRepository;

    public UserEntityService(UserEntityRepository<E> userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * @param email the `email` identifying the user
     * @return UserDetails
     * @throws EmailNotFoundException if email is not found
     */
    @Override
    public UserEntity loadUserByUsername(String email) throws EmailNotFoundException {
        return userEntityRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(email)
        );
    }
}
