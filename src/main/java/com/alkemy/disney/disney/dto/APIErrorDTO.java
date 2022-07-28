package com.alkemy.disney.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Getter
@Setter
public class APIErrorDTO <T> extends Object{

    // Useful to manage API exceptions
    private HttpStatus status;
    private T errors;
}
