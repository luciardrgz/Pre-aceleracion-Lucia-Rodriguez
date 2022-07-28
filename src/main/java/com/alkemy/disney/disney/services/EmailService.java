package com.alkemy.disney.disney.services;

import java.io.IOException;

public interface EmailService {
    void sendWelcomeEmailTo(String to) throws IOException;
}
