package com.halilkrkn.usersapi.service;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.data.mapper.ModelMapperService;
import com.halilkrkn.usersapi.data.repository.UserRepository;
import com.halilkrkn.usersapi.exception.ResourceNotFoundException;
import com.halilkrkn.usersapi.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new ResourceNotFoundException("Email is already taken");

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
            throw new ResourceNotFoundException("User not found with id: "+ id);
        }
        return userRepository.findById(id).map(user -> modelMapperService.entityToDto().map(user, UserDto.class));
    }

    // Update İşlemi
    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        User userUpdate = modelMapperService.dtoToEntity().map(userDto, User.class);
        User userUpdateFindById = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id: "+ id));
        userUpdateFindById.setName(userUpdate.getName());
        userUpdateFindById.setSurname(userUpdate.getSurname());
        userUpdateFindById.setEmail(userUpdate.getEmail());
        userUpdateFindById.setPassword(userUpdate.getPassword());

        User user = userRepository.save(userUpdateFindById);

        return modelMapperService.entityToDto().map(user, UserDto.class);
    }


    // Delete İşlemi
    @Override
    public UserDto deleteByIdUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ id));
        userRepository.deleteById(id);
        return modelMapperService.entityToDto().map(user, UserDto.class);
    }
}
