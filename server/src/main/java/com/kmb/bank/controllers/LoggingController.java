package com.kmb.bank.controllers;

import com.kmb.bank.services.LoggingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;


@Controller
public class LoggingController {

    @Autowired
    private LoggingService loggingService;


    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/home")
    public ResponseEntity<String> home1(){
        URI location = URI.create("/home");
        return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @RequestMapping(value = "/password" , method=RequestMethod.POST)
    public String password(HttpServletRequest req,@RequestParam(value = "username", required = false) String username,@RequestParam(value = "error", required = false) String error){
        if(username == null && error == null) {
            return "redirect:login";
        }else if(username.equals("elo") && error==null){
            return "password";
        }else if(error!=null){
            return "password";
        }else{
            return "redirect:login?error";
        }
    }

    @RequestMapping(value="/password", method=RequestMethod.GET)
    public String passwordGet(){
        return "redirect:login";
    }

    @RequestMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }
}
