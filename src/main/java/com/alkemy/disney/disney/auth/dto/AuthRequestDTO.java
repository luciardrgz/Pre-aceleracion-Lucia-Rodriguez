package com.alkemy.disney.disney.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthRequestDTO implements Serializable {
    private static final Long serialVersionUID = 1L; // Serializable version ID

    private String username;
    private String password;
}
