package by.haidash.blog.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import by.haidash.blog.server.model.entity.User;
import by.haidash.blog.server.repository.UserRepository;

@Component
public class BasicUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public BasicUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        final Optional<User> userOption = userId.contains("@") ? userRepository.findOneByEmail(userId) : userRepository.findOneByUsername(userId);
        final User user = userOption.orElseThrow(() -> new UsernameNotFoundException(String.format("User with username or email '%s' was not found", userId)));
        return user;

    }
}
