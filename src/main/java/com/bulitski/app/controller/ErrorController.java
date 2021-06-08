package com.bulitski.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/Error")
    public String getPage() {
        return "Error";
    }
}
