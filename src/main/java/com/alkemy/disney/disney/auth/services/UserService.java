package com.alkemy.disney.disney.auth.services;

import com.alkemy.disney.disney.auth.dto.AuthRequestDTO;
import com.alkemy.disney.disney.auth.dto.AuthResponseDTO;
import com.alkemy.disney.disney.auth.dto.UserDTO;
import com.alkemy.disney.disney.auth.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

public interface UserService {

    UserEntity getUser(Long id) throws NoSuchElementException;
    UserEntity getUser(String username) throws NoSuchElementException;
    UserDTO registerUser(UserDTO dto) throws Exception;
    AuthResponseDTO login(AuthRequestDTO dto);
    AuthResponseDTO refreshToken(HttpServletRequest request) throws Exception;
}
