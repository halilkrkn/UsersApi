package com.halilkrkn.usersapi.service;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.model.User;

import java.util.List;


public interface UserService {

     void addUser(UserDto userDto);
     List<UserDto> findAll();

    // Model Mapper
//    public  EmployeeDto entityToDto(EmployeeEntity employeeEntity);
//    public  EmployeeEntity dtoToEntity(EmployeeDto employeeDto);
}
