package com.alkemy.disney.disney.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO implements Serializable {
    private static final Long serialVersionUID = 1L; // Serializable version ID

    private String accessToken;
    private String refreshToken;
}
