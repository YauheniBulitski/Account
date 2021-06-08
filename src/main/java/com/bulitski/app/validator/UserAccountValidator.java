package com.bulitski.app.validator;

import com.bulitski.app.constant.NumberConstant;
import com.bulitski.app.dto.UserAccountDto;
import com.bulitski.app.service.UserServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@NoArgsConstructor
public class UserAccountValidator implements CustomValidator<UserAccountDto> {

    private UserServiceImpl userService;

    @Autowired
    public UserAccountValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void check(UserAccountDto userAccountDto, Errors errors) {

        if (userAccountDto.getUserName().length() < NumberConstant.THREE || userAccountDto.getUserName().length() > NumberConstant.SIXTEEN) {
            errors.rejectValue("userName", "1", "err.login.length");
        } else if (!userAccountDto.getUserName().matches("[a-zA-Z]+")) {
            errors.rejectValue("userName", "2", "err.login. letters");
        } else if (userService.findByUserName(userAccountDto.getUserName()) != null) {
            errors.rejectValue("userName", "3", "err.login.users");
        }

            if (userAccountDto.getPassword().length() < NumberConstant.THREE || userAccountDto.getPassword().length() > NumberConstant.SIXTEEN) {
                errors.rejectValue("password", "4", "err.pass.length");
            } else if (!userAccountDto.getPassword().matches("[a-zA-Z0-9]+")) {
                errors.rejectValue("password", "5", "err.pass.letters");
            } else if (!userAccountDto.getPassword().matches("[a-zA-Z].*\\d|\\d.*[a-zA-Z]")) {
                errors.rejectValue("password", "6", "err.pass.input");
            }

        if (userAccountDto.getFirstName().length() < NumberConstant.ONE || userAccountDto.getFirstName().length() > NumberConstant.SIXTEEN) {
            errors.rejectValue("firsName", "7", "err.name.length");
        } else if (!userAccountDto.getFirstName().matches("[a-zA-Z]+")) {
            errors.rejectValue("firsName", "8", "err.name.letter");
        }

        if (userAccountDto.getLastName().length() < NumberConstant.ONE || userAccountDto.getLastName().length() > NumberConstant.SIXTEEN) {
            errors.rejectValue("lastName", "9", "err.lastname.length");
        } else if (!userAccountDto.getLastName().matches("[a-zA-Z]+")) {
            errors.rejectValue("lastName", "10 ", "err.lastname.letter");
        }
    }
}
