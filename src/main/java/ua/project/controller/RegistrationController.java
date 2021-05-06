package ua.project.controller;


import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.project.controller.dto.UserDto;
import ua.project.exceptions.UserAlreadyExistException;
import ua.project.services.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private static final Logger logger = (Logger) LogManager.getLogger(RegistrationController.class);
    @Qualifier("userServiceImpl")
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration(Model model ){
        logger.debug("requested /registration get method");
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        logger.debug("returning registration.html to user");
        return "registration";
    }
    @PostMapping("/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model) {
        if(bindingResult.hasErrors()){
            logger.debug("userDto has errors , returning to registration.html");
            return new ModelAndView("registration", "user", userDto);
        }
        try {
            userService.createAndSaveUser(userDto);
        } catch (UserAlreadyExistException ex) {
            logger.debug("user already exists , returning to registration.html");
            model.addAttribute("name" , ex.getMessage());
            return new ModelAndView("registration", "user", userDto);
        }
        logger.debug("returning login.html to user");
        return new ModelAndView("login", "user", userDto);
    }
}
