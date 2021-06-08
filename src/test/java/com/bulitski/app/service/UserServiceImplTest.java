package com.bulitski.app.service;


import com.bulitski.app.model.Role;
import com.bulitski.app.model.Status;
import com.bulitski.app.model.UserAccount;
import com.bulitski.app.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserAccountRepository userAccountRepository;

    @InjectMocks
    private static UserServiceImpl userService;

    @Test
    public void findByIdTest(){
        Long id= 1L;
        Optional<UserAccount> user= Optional.ofNullable(UserAccount.builder()
                .id(id)
                .userName("Admin")
                .firstName("Petya")
                .lastName("Petrow")
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .build());
        doReturn(user).when(userAccountRepository).findById(id);
        UserAccount newUser=userService.findById(id);
        assertThat(newUser).isEqualTo(user.get());
    }
}
