package com.bulitski.app.controller;

import com.bulitski.app.model.Role;
import com.bulitski.app.model.Status;
import com.bulitski.app.dto.UserAccountDto;
import com.bulitski.app.model.UserAccount;
import com.bulitski.app.service.UserServiceImpl;
import com.bulitski.app.utils.UserAccountMapper;
import com.bulitski.app.validator.UserAccountEditValidator;
import com.bulitski.app.validator.UserAccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final UserAccountValidator userAccountValidator;
    private final UserAccountEditValidator userAccountEditValidator;
    private final UserServiceImpl userService;

    @PostMapping(value = "/user/save")
    public String saveUser(@Validated UserAccountDto userAccountDto,
                           BindingResult bindingResult) {
        userAccountValidator.check(userAccountDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/user/new";
        }
        userService.save(userAccountDto);
        return "redirect:/user";
    }

    @GetMapping(value = "/user/{id}/edit")
    public ModelAndView getFormEdit(ModelAndView model,
                                    @PathVariable Long id) {

        UserAccount userAccount = userService.findById(id);
UserAccountDto userAccountDto= UserAccountMapper.INSTANCE.userAccountToUserAccountDto(userAccount);
        model.addObject("userAccountDto",userAccountDto)
                .setViewName("edit");
//        model.addObject("userAccountDto", UserAccountDto.builder()
//                .id(userAccount.getId())
//                .userName(userAccount.getUserName())
//                .firsName(userAccount.getFirstName())
//                .lastName(userAccount.getLastName())
//                .role(userAccount.getRole())
//                .status(userAccount.getStatus())
//                .build())
//                .setViewName("edit");

        return model;
    }

    @PostMapping(value = "/user/update")
    public String updateUser(@Validated UserAccountDto userAccountDto,
                             BindingResult bindingResult) {
        userAccountEditValidator.check(userAccountDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        userService.update(userAccountDto);
        return "redirect:/user/" + userAccountDto.getId();
    }


    @GetMapping("/lock")
    public String lock(HttpServletRequest request,
                       @RequestParam Long id,
                       @RequestParam String status) {
        if (status.equals("lock")) {
            userService.updateStatus(Status.INACTIVE, id);
        } else {
            userService.updateStatus(Status.ACTIVE, id);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/role")
    public String role(HttpServletRequest request,
                       @RequestParam Long id,
                       @RequestParam String role) {
        userService.updateRole(Role.valueOf(role), id);
        return "redirect:" + request.getHeader("referer");
    }


}
