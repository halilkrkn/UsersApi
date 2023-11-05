package com.halilkrkn.usersapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.halilkrkn.usersapi.data.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserServiceTest {
    void addUserTestIsSuccessfully();
    void findAllTest();
    void findByIdTest();
    void updateUserTest();
    void deleteByIdUserTest();
}
