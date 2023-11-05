package com.halilkrkn.usersapi;

import com.halilkrkn.usersapi.data.repository.UserRepository;
import com.halilkrkn.usersapi.model.User;
import com.halilkrkn.usersapi.service.UserServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = UsersApiApplication.class)
@AutoConfigureMockMvc
//@RunWith(MockitoJUnitRunner.class)
class UsersApiApplicationTests implements UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Autowired
    UserRepository userRepository;

    @Test
    public void givenUsers_whenGetUsers_thenStatus200()
            throws Exception {

        assertThat(userRepository.findAll()).isNotNull();
    }

    @Test
    @Override
    public void addUserTestIsSuccessfully() {
        User user = User
                .builder()
                .name("Halil")
                .surname("Karkim")
                .email("halil@test.com")
                .password("123456")
                .build();

        userRepository.save(user);
        assertNotNull(userRepository.findById(1));

    }

    @Override
    public void findAllTest() {

    }

    @Override
    public void findByIdTest() {

    }

    @Override
    public void updateUserTest() {

    }

    @Override
    public void deleteByIdUserTest() {

    }
}
