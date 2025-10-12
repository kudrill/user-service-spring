package com.example.user_service.dto;

import java.time.LocalDateTime;

public record UserDto(Long id, String name, String email, Integer age, LocalDateTime createdAt) {}
