package com.kmb.bank.services;


import com.kmb.bank.db.postgres.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log4j2
@Service
public class LoggingService {


    @Autowired
    private UserRepository userRepository;

    public Boolean validateUsername(String username, Model model) {
        Optional<String> color =  Optional.ofNullable(userRepository.getUserColor(username));
        if (color.isPresent()) {
            model.addAttribute("userColor", color.get());
            return true;
        }
        return false;
    }

    public boolean validatePassword(HttpServletRequest request, Model model, String username, String password) {
        int validation = userRepository.validateUsernameAndPassword(username, password);
        if (validation > 0) {
            saveSession(request, username);
            return true;
        }
        Optional <String> color = Optional.ofNullable(userRepository.getUserColor(username));
        color.ifPresent(a -> model.addAttribute("userColor", a));
        return false;
    }

    public void saveSession(HttpServletRequest request, String username) {
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("nameSurname", getNameFromUsername(username));
    }

    public String getNameFromUsername(String username) {
        return Optional.ofNullable(userRepository.getUserNameAndSurname(username)).orElse("");
    }

    public boolean ifUserLogged(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession().getAttribute("username")).isPresent();
    }
}
