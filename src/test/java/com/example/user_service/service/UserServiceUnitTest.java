package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceUnitTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(repository, kafkaProducerService);
    }

    @Test
    void testCreateUser() {
        UserDto dto = new UserDto(null, "Alice", "alice@test.com", 25, LocalDateTime.now());
        User user = new User();
        user.setId(1L);
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setAge(dto.age());
        user.setCreatedAt(dto.createdAt());

        when(repository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.create(dto);

        assertEquals(1L, result.id());
        assertEquals("Alice", result.name());
        verify(kafkaProducerService, times(1)).sendUserEvent("CREATE:alice@test.com");
    }

    @Test
    void testGetByIdUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.getById(1L));
        assertEquals("User not found", ex.getMessage());
    }
}
