package com.alkemy.disney.disney.auth.services;

import com.alkemy.disney.disney.auth.dto.AuthRequestDTO;
import com.alkemy.disney.disney.auth.dto.AuthResponseDTO;
import com.alkemy.disney.disney.auth.dto.UserDTO;
import com.alkemy.disney.disney.auth.entity.UserEntity;
import com.alkemy.disney.disney.auth.repository.UserRepository;
import com.alkemy.disney.disney.exceptions.ExistentUserExc;
import com.alkemy.disney.disney.services.EmailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
    }

    @Override // Searches a User by its id and retrieves it from the Users Repository
    public UserEntity getUser(Long id) throws NoSuchElementException {
        return userRepository.findById(id).orElseThrow();
    }

    @Override // Searches a User by its username and retrieves it from the Users Repository
    public UserEntity getUser(String username) throws NoSuchElementException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override // Searches a User by email in Users Repo: if it doesn't exist, DTO->Entity and save it in Repository
    public UserDTO registerUser(UserDTO dto) throws Exception {
        if(userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ExistentUserExc("That email is already registered");
        }

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // User's password gets encrypted
        user = userRepository.save(user);

        dto.setId(user.getId());
        dto.setPassword(user.getPassword());

        if(user != null) {
            emailService.sendWelcomeEmailTo(user.getUsername()); // If successful, send mail to the newly registered user
        }
        return dto;
    }

    @Override // Authenticates the User that is making the login request and gives him his tokens if successful
    public AuthResponseDTO login(AuthRequestDTO dto) {
        UserDetails userDetails;

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(),
                dto.getPassword()));

        userDetails = (UserDetails) auth.getPrincipal();

        String accessToken = JwtUtils.createAccessToken(userDetails);
        String refreshToken = JwtUtils.createRefreshToken(userDetails);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    @Override // Gives the User a refresh token
    public AuthResponseDTO refreshToken(HttpServletRequest request) throws Exception {
        String authenticationHeader = request.getHeader("Authorization");

        if(authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {

            String refreshToken = authenticationHeader.substring("Bearer ".length());
            String username = JwtUtils.decodeToken(refreshToken);

            UserEntity user = getUser(username);
            if(user == null) {
                throw new Exception();
            }

            String accessToken = JwtUtils.createAccessToken(user);
            return new AuthResponseDTO(accessToken, refreshToken);
        } else
        {
            throw new Exception();
        }
    }
}
