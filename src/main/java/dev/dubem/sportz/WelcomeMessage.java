package dev.dubem.sportz;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {

    public String getWelcomeMessage()
    {
        return "Welcome to new spring project....";
    }
}
