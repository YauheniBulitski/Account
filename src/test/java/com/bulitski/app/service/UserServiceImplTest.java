package com.bulitski.app.service;


import com.bulitski.app.converter.UserDetailsConverter;
import com.bulitski.app.model.Role;
import com.bulitski.app.model.Status;
import com.bulitski.app.model.UserAccount;
import com.bulitski.app.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userServiceImpl;
    private UserAccountRepository userAccountRepository;


    @BeforeEach
    public void init(){
        userAccountRepository= mock(UserAccountRepository.class);
        UserDetailsConverter userDetailsConverter= mock(UserDetailsConverter.class);
        userServiceImpl=new UserServiceImpl(userAccountRepository,userDetailsConverter,passwordEncoder);
    }

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
        when(userAccountRepository.findById(id)).thenReturn(user);
        UserAccount userAccount=userServiceImpl.findById(id);
        verify(userAccountRepository, times(1)).findById(id);
        assertThat(userAccount).isEqualTo(user.get());
    }

}
