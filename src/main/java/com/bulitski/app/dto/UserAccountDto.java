package com.bulitski.app.dto;

import com.bulitski.app.model.Role;
import com.bulitski.app.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {

    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Status status;
    private LocalDate createdAt;
    private Role role;
}
