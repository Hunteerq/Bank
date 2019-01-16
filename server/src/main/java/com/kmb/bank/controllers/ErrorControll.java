package com.kmb.bank.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControll implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String getErrorPage(HttpServletRequest request) {
        return "redirect:/";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
