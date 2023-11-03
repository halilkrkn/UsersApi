package com.halilkrkn.usersapi.service;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.data.mapper.ModelMapperService;
import com.halilkrkn.usersapi.data.repository.UserRepository;
import com.halilkrkn.usersapi.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;


    @Override
    public void addUser(UserDto userDto) {
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email is already taken");
        }
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream()
                .map(user -> modelMapperService.entityToDto().map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }
}
