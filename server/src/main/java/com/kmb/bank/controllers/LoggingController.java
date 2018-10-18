package com.kmb.bank.controllers;

import com.kmb.bank.models.Address;
import com.kmb.bank.models.Transaction;
import com.kmb.bank.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    }

    @RequestMapping("/testRabbit")
    public void testRabbit() {
        Address address = new Address(1, "Podchorazych", 15, 6, "30-711", "Poland");
        Transaction transaction = new Transaction(1, "666223452626", "15523632673470", LocalDateTime.now(), 421.53, "Za Merte", 1, address );
        loggingService.testRabbitMq(transaction);

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

    @GetMapping("/check")
    public String check(){
        return "redirect:login";
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
