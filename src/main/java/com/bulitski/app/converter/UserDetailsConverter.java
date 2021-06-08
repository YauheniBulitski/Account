package com.bulitski.app.converter;

import com.bulitski.app.model.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsConverter implements Converter<UserAccount, UserDetails> {

    @Override
    public UserDetails convert(UserAccount userAccount) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(userAccount.getUserName())
                .password(userAccount.getPassword())
                .authorities(userAccount.getRole().name())
                .build();
    }
}
