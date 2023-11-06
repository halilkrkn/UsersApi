package com.halilkrkn.usersapi.service;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.data.mapper.ModelMapperService;
import com.halilkrkn.usersapi.data.mapper.ModelMapperServiceImpl;
import com.halilkrkn.usersapi.data.repository.UserRepository;
import com.halilkrkn.usersapi.exception.ResourceNotFoundException;
import com.halilkrkn.usersapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private final ModelMapper modelMapper = new ModelMapper();
    private final ModelMapperService modelMapperService = new ModelMapperServiceImpl(modelMapper);
    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, modelMapperService);
        userDto = UserDto
                .builder()
                .id(1)
                .name("Halil")
                .surname("Karkin")
                .email("halil@test.com")
                .password("123456")
                .build();


    }
    @DisplayName("JUnit test for addUser method")
    @Test
    public void givenUser_whenAddUser_thenReturnUserAdded() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userRepository.findByEmail(userDto.getEmail())).willReturn(Optional.empty());

        //when
        UserDto userDto = userService.addUser(this.userDto);


        //then
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getSurname(), userDto.getSurname());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());

        verify(userRepository, times(1)).save(user);

    }

    @DisplayName("JUnit test for addUser method which throws exception")
    @Test
    public void givenUser_whenAddUser_thenThrowException() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findByEmail(userDto.getEmail())).willReturn(Optional.of(user));

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            userRepository.save(user);
            userService.addUser(userDto);
        });

        //then
        try {
            userService.addUser(userDto);
        } catch (ResourceNotFoundException e) {
            assertEquals("Email is already taken", e.getMessage());
        }
    }

    @DisplayName("JUnit test for findAllUsers method")
    @Test
    public void givenUsers_whenFindAll_thenReturnAllUsers() {
        User user1 = User
                .builder()
                .id(2)
                .name("Halil")
                .surname("Karkin")
                .email("halil1@test.com")
                .password("123456")
                .build();

        User user2 = User
                .builder()
                .id(3)
                .name("ibrahim")
                .surname("Karkin")
                .email("ibrahim@test.com")
                .password("123456")
                .build();

        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findAll()).willReturn(java.util.List.of(user,user1,user2));

        //when
        java.util.List<UserDto> userDtoList = userService.findAll();

        //then
        assertEquals(userDtoList.size(), 3);
        assertEquals(userDtoList.get(0).getId(), user.getId());
        assertEquals(userDtoList.get(0).getName(), user.getName());
        assertEquals(userDtoList.get(0).getSurname(), user.getSurname());
        assertEquals(userDtoList.get(0).getEmail(), user.getEmail());
        assertEquals(userDtoList.get(0).getPassword(), user.getPassword());

        verify(userRepository, times(1)).findAll();
    }

    @DisplayName("JUnit test for findAllUsers method which throws exception")
    @Test
    public void givenUsers_whenFindAll_thenThrowException() {
        //given
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        //when
        //then
        try {
            userService.findAll();
        } catch (ResourceNotFoundException e) {
            assertEquals("Users not found", e.getMessage());
        }
    }

    @DisplayName("JUnit test for FindByIdUser method")
    @Test
    public void givenUser_whenFindById_thenReturnUser() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findById(userDto.getId())).willReturn(Optional.of(user));

        //when
        Optional<UserDto> userDto = userService.findById(user.getId());

        //then
        assertEquals(userDto.get().getId(), user.getId());
        assertEquals(userDto.get().getName(), user.getName());
        assertEquals(userDto.get().getSurname(), user.getSurname());
        assertEquals(userDto.get().getEmail(), user.getEmail());
        assertEquals(userDto.get().getPassword(), user.getPassword());

        verify(userRepository, times(2)).findById(user.getId());
    }

    @DisplayName("JUnit test for FindByIdUser method which throws exception")
    @Test
    public void givenUser_whenFindById_thenThrowException() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findById(userDto.getId())).willReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(user.getId());
        });

        //then
        try {
            userService.findById(user.getId());
        } catch (ResourceNotFoundException e) {
            assertEquals("User not found with id: " + user.getId(), e.getMessage());
        }
    }

    @DisplayName("JUnit test for updateUser method")
    @Test
    public void givenUser_whenUpdateUser_thenReturnUserUpdated() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findById(userDto.getId())).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);

        //when
        UserDto userDto = userService.updateUser(user.getId(), this.userDto);

        //then
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getSurname(), user.getSurname());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());

        verify(userRepository, times(1)).save(user);
    }

    @DisplayName("JUnit test for updateUser method which throws exception")
    @Test
    public void givenUser_whenUpdateUser_thenThrowException() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findById(userDto.getId())).willReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(user.getId(), userDto);
        });

        //then
        try {
            userService.updateUser(user.getId(), userDto);
        } catch (ResourceNotFoundException e) {
            assertEquals("User not exist with id: " + user.getId(), e.getMessage());
        }
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void givenUser_whenDeleteUser_thenReturnUserDeleted() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findById(userDto.getId())).willReturn(Optional.of(user));

        //when
        UserDto userDto = userService.deleteByIdUser(user.getId());

        //then
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getSurname(), user.getSurname());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @DisplayName("JUnit test for deleteUser method which throws exception")
    @Test
    public void givenUser_whenDeleteUser_thenThrowException() {
        //given
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        given(userRepository.findById(userDto.getId())).willReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteByIdUser(user.getId());
        });

        //then
        try {
            userService.deleteByIdUser(user.getId());
        } catch (ResourceNotFoundException e) {
            assertEquals("User not found with id: " + user.getId(), e.getMessage());
        }
    }
}
