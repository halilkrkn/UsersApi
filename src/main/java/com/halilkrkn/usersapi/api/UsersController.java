package com.halilkrkn.usersapi.api;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
// örnek url: http://localhost:8080/api/v1/users
public class UsersController {
    private UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return "User added";
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }
}
