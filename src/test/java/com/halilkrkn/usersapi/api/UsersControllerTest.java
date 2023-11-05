package com.halilkrkn.usersapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.data.mapper.ModelMapperService;
import com.halilkrkn.usersapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapperService modelMapper;

    @Test
    public void givenUser_whenAddUser_thenReturnCreatedUser_ok_200() throws Exception {
        // Given
        UserDto userDto = UserDto
                .builder()
                .id(1)
                .name("Halil")
                .surname("Karkin")
                .email("halil@test.com")
                .password("123456")
                .build();

        // When
        when(userService.addUser(any(UserDto.class))).thenReturn(userDto);
//        when(userService.addUser(any(UserDto.class))).thenReturn(createdUser);

        // Then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Halil"))
                .andExpect(jsonPath("$.surname").value("Karkin"))
                .andExpect(jsonPath("$.email").value("halil@test.com"))
                .andExpect(jsonPath("$.password").value("123456"));
    }

    @Test
    public void givenUser_whenAddUser_thenReturnCreatedUser_badRequest_400() throws Exception {
        // Given
        UserDto userDto = UserDto
                .builder()
                .id(null)
                .build();

        // When
        when(userService.addUser(any(UserDto.class))).thenReturn(userDto);

        // Then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUserId_whenFindById_thenReturnUser_ok_200() throws Exception {
        // Given
        int userId = 1;
        UserDto userDto = UserDto
                .builder()
                .id(1)
                .name("Halil")
                .surname("Karkin")
                .email("halil@test.com")
                .password("123456")
                .build();

        // When
//        when(userService.findById(any(Integer.class))).thenReturn(java.util.Optional.of(userDto));
        when(userService.findById(userId)).thenReturn(Optional.ofNullable(Optional.of(userDto).orElseThrow(() -> new IllegalStateException("User not found"))));

        // Then
        mockMvc.perform(get("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUserId_whenFindById_thenReturnUser_notFound_404() throws Exception {
        // Given
        int userId = 1;
        int notUserId = 2;
        UserDto userDto = UserDto
                .builder()
                .id(1)
                .name("Halil")
                .surname("Karkin")
                .email("halil@test.com")
                .password("123456")
                .build();

        // When
        when(userService.findById(userId)).thenReturn(Optional.of(Optional.ofNullable(userDto).orElseThrow()));

        // Then
        mockMvc.perform(get("/api/v1/users/{id}", notUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenUserFindAll_whenFindAll_thenReturnUser_ok_200() throws Exception {
        // Given
        UserDto user1 = UserDto
                .builder()
                .id(1)
                .name("Halil")
                .surname("Karkin")
                .email("halil@test.com")
                .password("123456")
                .build();

        UserDto user2 = UserDto
                .builder()
                .id(2)
                .name("İbrahim")
                .surname("Karkin")
                .email("ibrahim@test.com")
                .password("selam123")
                .build();
        UserDto user3 = UserDto
                .builder()
                .id(3)
                .name("Halil İbrahim")
                .surname("Karkin")
                .email("halilibrahimkarkin@test.com")
                .password("ibrahim_80")
                .build();

        List<UserDto> userDto = List.of(user1, user2, user3);

        // When
        when(userService.findAll()).thenReturn(userDto);

        // Then
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUserFindAll_whenFindAll_thenReturnUser_notFound_404() throws Exception {
        // Given
        // When
        when(userService.findAll()).thenReturn(Collections.emptyList());
        // Then
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Collections.emptyList())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser_ok_200() throws Exception {
        // Given
        int userId = 1;
        UserDto userDto = UserDto
                .builder()
                .id(1)
                .name("Halil")
                .surname("Karkin")
                .email("halil@test.com")
                .password("123456")
                .build();

        // When
        when(userService.updateUser(any(Integer.class), any(UserDto.class))).thenReturn(userDto);

        // Then
        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Halil"))
                .andExpect(jsonPath("$.surname").value("Karkin"))
                .andExpect(jsonPath("$.email").value("halil@test.com"))
                .andExpect(jsonPath("$.password").value("123456"));
    }

    @Test
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser_badRequest_400() throws Exception {
        // Given
        int userId = 1;
        UserDto userDto = UserDto
                .builder()
                .id(1)
                .name("")
                .build();

        // When
        when(userService.updateUser(any(Integer.class), any(UserDto.class))).thenReturn(userDto);

        // Then
        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUserId_whenDeleteByIdUser_thenReturnDeletedUser_ok_200() throws Exception {
        // Given
        int userId = 1;
        UserDto userDto = UserDto
                .builder()
                .id(1)
                .name("Halil")
                .surname("Karkin")
                .email("halil@test.com")
                .password("123456")
                .build();

        // When
        when(userService.deleteByIdUser(any(Integer.class))).thenReturn(userDto);

        // Then
        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isNotFound());
    }
}