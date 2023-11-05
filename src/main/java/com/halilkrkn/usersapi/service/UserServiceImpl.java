package com.halilkrkn.usersapi.service;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.data.mapper.ModelMapperService;
import com.halilkrkn.usersapi.data.repository.UserRepository;
import com.halilkrkn.usersapi.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;


    // Add İşlemi
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new ResolutionException("Email is already taken");

        }
        userRepository.save(user);
        return userDto;
    }

    // List İşlemi
    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream()
                .map(user -> modelMapperService.entityToDto().map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }


    // Find İşlemi
    @Override
    public Optional<UserDto> findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User not found with id: "+ id);
        }
        return userRepository.findById(id).map(user -> modelMapperService.entityToDto().map(user, UserDto.class));
    }

    // Update İşlemi
    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        User user = modelMapperService.dtoToEntity().map(userDto, User.class);
        User userUpdateFindById = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not exist with id: "+ id));
        userUpdateFindById.setName(user.getName());
        userUpdateFindById.setSurname(user.getSurname());
        userUpdateFindById.setEmail(user.getEmail());
        userUpdateFindById.setPassword(user.getPassword());

        User userUpdate = userRepository.save(userUpdateFindById);
        UserDto userDtoUpdate = modelMapperService.entityToDto().map(userUpdate, UserDto.class);

        return userDtoUpdate;
    }

    // Delete İşlemi
    @Override
    public UserDto deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found with id: "+ id));
        userRepository.deleteById(id);
        UserDto userDeleteDto = modelMapperService.entityToDto().map(user, UserDto.class);
        return userDeleteDto;
    }
}
