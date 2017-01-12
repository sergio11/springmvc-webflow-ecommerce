package web.frontend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.User;
import web.services.UserService;

/**
 * @author sergio
 */
@Controller("FrontendSignupController")
@RequestMapping("/signup")
@SessionAttributes({SignupController.ATTRIBUTE_NAME})
public class SignupController {
    
    private static Logger logger = LoggerFactory.getLogger(SignupController.class);
    public static final String ATTRIBUTE_NAME = "user";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String signup(Model model){
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME,  new User());
        }
        return "frontend/account/signup";
    }
    
    @PostMapping
    public String signup(
        @Validated(User.UserCreation.class) @ModelAttribute(ATTRIBUTE_NAME) User user,
        BindingResult bindingResult,
        RedirectAttributes model,
        // SessionStatus lets you clear your SessionAttributes
        SessionStatus sessionStatus) {
        
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return "redirect:/signup";
        }
        userService.create(user);
        sessionStatus.setComplete(); //remove user from session
        return "redirect:/login";
    }
    
}
