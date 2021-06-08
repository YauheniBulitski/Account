package com.bulitski.app.controller;

import com.bulitski.app.dto.UserAccountDto;
import com.bulitski.app.model.Role;
import com.bulitski.app.model.UserAccount;
import com.bulitski.app.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/user")
    public ModelAndView getUsers(ModelAndView model,
                                 @RequestParam(defaultValue = "user") String userName,
                                 @RequestParam (defaultValue = "ROLE")String role,
                                 @RequestParam(defaultValue = "0") int pageNo,
                                 @RequestParam(defaultValue = "4") int pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {

        List<UserAccount> users;
        if (userName.equals("null") && role.equals("null")) {
            model.addObject("users", userService.findAll(pageNo, pageSize, sortBy))
                    .addObject("pageNo", pageNo)
                    .addObject("pageSize", pageSize);
            model.setViewName("List");
        } else if (!userName.equals("user") && role.equals("ROLE")) {
            users = userService.findAllMatch(userName, pageNo, pageSize, sortBy);
            model.addObject("users", users)
                    .addObject("userName",userName)
                    .addObject("pageNo", pageNo)
                    .addObject("pageSize", pageSize)
                    .setViewName("List");
            return model;
        } else if (!userName.equals("user") && !role.equals(null)) {
            users = userService.findAllMatchByRole(userName, Role.valueOf(role), pageNo, pageSize, sortBy);
            model.addObject("users", users) .addObject("pageNo", pageNo)
                    .addObject("userName",userName)
                    .addObject("role",role)
                    .addObject("pageSize", pageSize)
                    .setViewName("List");
            return model;
        } else if (userName.equals("user") && !role.equals("ROLE")) {
            users = userService.findByRole(Role.valueOf(role), pageNo, pageSize, sortBy);
            model.addObject("users", users)
                    .addObject("pageNo", pageNo)
                    .addObject("role",role)
                    .addObject("pageSize", pageSize)
                    .setViewName("List");
            return model;
        }
        model.addObject("users", userService.findAll(pageNo, pageSize, sortBy))
                .addObject("pageNo", pageNo)
                .addObject("pageSize", pageSize);
        model.setViewName("List");
        return model;
    }

    @GetMapping("/user/{id}")
    public String getUser(Model model,
                          @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));
        return "View";
    }

    @GetMapping("/user/new")
    public Model newUser(Model model) {
        model.addAttribute("userAccountDto", new UserAccountDto());
        return model;
    }
}
