package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import com.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {

        userRepository.deleteAll();
    }

    @Test
    void createAndGetUser() {

        String uniqueEmail = "alice" + System.currentTimeMillis() + "@example.com";

        UserDto userDto = new UserDto(null, "Alice", uniqueEmail, 25, null);

        UserDto savedUser = userService.create(userDto);

        assertEquals("Alice", savedUser.name());
        assertEquals(uniqueEmail, savedUser.email());
    }

    @Test
    void createMultipleUsers() {
        for (int i = 0; i < 5; i++) {
            String uniqueEmail = "user" + System.currentTimeMillis() + i + "@example.com";

            UserDto userDto = new UserDto(null, "User" + i, uniqueEmail, 20 + i, null);
            userService.create(userDto);
        }


        assertEquals(5, userRepository.count());
    }
}
