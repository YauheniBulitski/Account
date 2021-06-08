package com.bulitski.app.service;

import com.bulitski.app.model.Role;
import com.bulitski.app.model.Status;
import com.bulitski.app.converter.UserDetailsConverter;
import com.bulitski.app.dto.UserAccountDto;
import com.bulitski.app.model.UserAccount;
import com.bulitski.app.repository.UserAccountRepository;
import com.bulitski.app.utils.UserAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userAccountRepository;
    private final UserDetailsConverter detailsConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userAccountRepository.findByUserName(name)
                .filter(it -> it.getStatus().equals(Status.ACTIVE))
                .map(detailsConverter::convert)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public List<UserAccount> findAll(int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        return userAccountRepository.findAll(pageable);
    }

    public UserAccount findById(Long id) {
        return userAccountRepository.findById(id).get();
    }

    @Transactional
    public void updateStatus(Status status, Long id) {
        userAccountRepository.updateStatus(status, id);
    }

    @Transactional
    public void updateRole(Role role, Long id) {
        userAccountRepository.updateRole(role, id);
    }

    public UserAccount findByUserName(String name) {
        return userAccountRepository.findByUserName(name).orElse(null);
    }

    @Transactional
    public UserAccount save(UserAccountDto userAccountDto) {
        UserAccount userAccount = UserAccountMapper.INSTANCE.userAccountDtoToUserAccount(userAccountDto, passwordEncoder, LocalDate.now());
        return save(userAccount);

    }

    public List<UserAccount> findAllMatch(String userName, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return userAccountRepository.findAllMatch(userName.toUpperCase(), pageable);
    }

    public List<UserAccount> findAllMatchByRole(String userName, Role role, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return userAccountRepository.findAllMatchByRole(userName.toUpperCase(), role, pageable);
    }

    public List<UserAccount> findByRole(Role role, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return userAccountRepository.findByRole(role, pageable);
    }

    public UserAccount update(UserAccountDto userAccountDto) {
        UserAccount userAccount = findById(userAccountDto.getId());

        userAccount.setUserName(userAccountDto.getUserName());
        userAccount.setFirstName(userAccountDto.getFirstName());
        userAccount.setLastName(userAccountDto.getLastName());
        userAccount.setStatus(userAccountDto.getStatus());
        userAccount.setRole(userAccountDto.getRole());

        if (!userAccountDto.getPassword().equals("")) {
            userAccount.setPassword(passwordEncoder.encode(userAccountDto.getPassword()));
        }

        return save(userAccount);
    }
}
