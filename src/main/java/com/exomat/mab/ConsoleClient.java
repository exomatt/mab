package com.exomat.mab;

import com.exomat.mab.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleClient implements CommandLineRunner {
    private final UserService userService;

    public ConsoleClient(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("test run ");
    }
}
