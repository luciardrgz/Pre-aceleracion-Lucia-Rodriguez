package com.alkemy.disney.disney.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Getter
@Setter
public class UserDTO implements Serializable {
    private static final Long serialVersionUID = 1L; // Serializable version ID
    private Long id;

    @Email(message = "Email not valid")
    private String username;

    @Size(min=8, message = "Password must have more than 8 characters")
    private String password;
}