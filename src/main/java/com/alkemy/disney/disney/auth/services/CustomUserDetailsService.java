package com.alkemy.disney.disney.auth.services;

import com.alkemy.disney.disney.auth.entity.UserEntity;
import com.alkemy.disney.disney.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override // Retrieves a User from Repository with its granted authorities
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).get();

        if(user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
