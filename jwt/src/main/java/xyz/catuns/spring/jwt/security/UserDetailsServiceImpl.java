package xyz.catuns.spring.jwt.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.catuns.spring.jwt.model.UserEntity;
import xyz.catuns.spring.jwt.model.UserRole;
import xyz.catuns.spring.jwt.repository.UserEntityRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public UserDetailsServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * @param username the `email` identifying the user
     * @return UserDetails
     * @throws UsernameNotFoundException if username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(UserRole::toAuthority)
                .collect(Collectors.toSet());

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
