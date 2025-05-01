package xyz.catuns.spring.base.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.catuns.spring.base.model.user.UserEntity;
import xyz.catuns.spring.base.model.user.UserRoleAuthority;
import xyz.catuns.spring.base.repository.user.UserRepository;

import java.util.List;

public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param username the `email` identifying the user
     * @return UserDetails
     * @throws UsernameNotFoundException if username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(UserRoleAuthority::asAuthority)
                .toList();

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
