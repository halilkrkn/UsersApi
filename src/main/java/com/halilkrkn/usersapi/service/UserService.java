package com.halilkrkn.usersapi.service;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    UserDto addUser(UserDto userDto);

    List<UserDto> findAll();

    Optional<UserDto> findById(Integer id);

    UserDto updateUser(Integer id, UserDto userDto);

    void userUpdates(UserDto userDto);

    UserDto deleteUser(Integer id);



    // Model Mapper
//    public  EmployeeDto entityToDto(EmployeeEntity employeeEntity);
//    public  EmployeeEntity dtoToEntity(EmployeeDto employeeDto);
}
