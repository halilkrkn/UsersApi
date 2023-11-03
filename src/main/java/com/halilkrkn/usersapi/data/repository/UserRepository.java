package com.halilkrkn.usersapi.data.repository;

import com.halilkrkn.usersapi.data.dto.UserDto;
import com.halilkrkn.usersapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
