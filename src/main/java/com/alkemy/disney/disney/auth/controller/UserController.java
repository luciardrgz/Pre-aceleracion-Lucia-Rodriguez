package com.alkemy.disney.disney.auth.controller;

import com.alkemy.disney.disney.auth.dto.AuthRequestDTO;
import com.alkemy.disney.disney.auth.dto.AuthResponseDTO;
import com.alkemy.disney.disney.auth.dto.UserDTO;
import com.alkemy.disney.disney.auth.services.UserServiceImpl;
import com.alkemy.disney.disney.exceptions.NonExistentEmailOrPassExc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register") // Invokes the User Service to save (register / sign up) a created User
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO dto) {
        try {
            return new ResponseEntity<>(userService.registerUser(dto), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/login") // Invokes the User Service to login / sign in a registered User
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto) throws NonExistentEmailOrPassExc {
        try {
            return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
        } catch(Exception e) {
            throw new NonExistentEmailOrPassExc("That email is not registered / Wrong password");
        }
    }

    @PostMapping("/refresh") // Retrieves a Refresh Token
    public ResponseEntity<AuthResponseDTO> refreshToken(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(userService.refreshToken(request), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}


