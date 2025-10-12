package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto getById(Long id);
    List<UserDto> getAll();
    UserDto update(Long id, UserDto dto);
    void delete(Long id);
}
