package com.halilkrkn.usersapi.api;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.model.User;
import com.halilkrkn.usersapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
// örnek url: http://localhost:8080/api/v1/users
// Örnek Swagger url: http://localhost:8080/swagger-ui.html
public class UsersController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto addUser = userService.addUser(userDto);
        if (addUser != null) {
            return ResponseEntity.ok(addUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> userFindAll = userService.findAll();
        if (userFindAll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userFindAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDto>> findById(@PathVariable Integer id) {
        Optional<UserDto> userFindById = userService.findById(id);
        if (userFindById.isPresent()) {
            return ResponseEntity.ok(userFindById);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        UserDto updateUser = userService.updateUser(id, userDto);
//        Optional<UserDto> user = userService.findById(id);
        if (updateUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdUser(@PathVariable Integer id) {
        UserDto deleted = userService.deleteByIdUser(id);
        if (deleted.getId() != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
