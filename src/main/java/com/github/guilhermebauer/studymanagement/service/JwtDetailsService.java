package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Service
public class JwtDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "An User with that email %s was not found!";

    private final UserRepository userRepository;

    @Autowired
    public JwtDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findUserByEmail(username).map( user->
        {
            List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName())).toList();

            return new User(user.getEmail(), user.getPassword(), authorities);
        }).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }
}
