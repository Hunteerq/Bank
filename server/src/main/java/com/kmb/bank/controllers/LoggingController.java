package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoggingController {

    @Autowired
    private LoggingService loggingService;


    @RequestMapping("/login")
    public ModelAndView loginController(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return new ModelAndView("first");
    }

}
