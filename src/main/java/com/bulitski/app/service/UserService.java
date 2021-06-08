package com.bulitski.app.service;

import com.bulitski.app.model.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserAccount save(UserAccount userAccount);

}
