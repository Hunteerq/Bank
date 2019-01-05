package com.kmb.bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping(value="/error")
    public String getErrorPage() {
        return "error";
    }
}
