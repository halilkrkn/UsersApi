package com.halilkrkn.usersapi.service;

import com.halilkrkn.usersapi.data.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface UserService {
    UserDto addUser(UserDto userDto);

    List<UserDto> findAll();

    Optional<UserDto> findById(Integer id);

    UserDto updateUser(Integer id, UserDto userDto);

    UserDto deleteByIdUser(Integer id);
}
