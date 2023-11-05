package com.halilkrkn.usersapi.api;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.model.User;
import com.halilkrkn.usersapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
// örnek url: http://localhost:8080/api/v1/users
public class UsersController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto addUser = userService.addUser(userDto);
        return ResponseEntity.ok(addUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> userFindAll = userService.findAll();
        return ResponseEntity.ok(userFindAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        UserDto userFindById = userService.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        return ResponseEntity.ok(userFindById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        UserDto updateUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Integer id) {
        UserDto deleteUser = userService.deleteUser(id);
        return ResponseEntity.ok(deleteUser);
    }
}
