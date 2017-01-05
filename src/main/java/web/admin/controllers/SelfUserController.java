package web.admin.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.User;
import persistence.repositories.UserRepository;
import security.CurrentUser;
import web.services.UserService;

/**
 * @author sergio
 */
@Controller
@RequestMapping("/admin/users/self")
@SessionAttributes({SelfUserController.ATTRIBUTE_NAME})
public class SelfUserController {
    
    private static Logger logger = LoggerFactory.getLogger(SelfUserController.class);
    
    public static final String ATTRIBUTE_NAME = "user";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
   
    
    @RequestMapping(method = RequestMethod.GET)
    public String self(@CurrentUser User user, Model model) {
        logger.info(user.toString());
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME, user);
        }
        return "admin/dashboard/user/profile";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String self(
        // @ModelAttribute will load User from session but also set values from the form post
       @Validated(User.UserUpdate.class) @ModelAttribute(ATTRIBUTE_NAME) User user,
       BindingResult bindingResult, 
       RedirectAttributes redirectAttributes,
       // SessionStatus lets you clear your SessionAttributes
       SessionStatus sessionStatus
    ){
        
        logger.info("Usuario a actualizar: " + user.toString());
        
        if(bindingResult.hasErrors()) {
            //put the validation errors in Flash session and redirect to self
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return "redirect:/admin/users/self";
        }
        
        userRepository.save(user);
        
        sessionStatus.setComplete(); //remove user from session

        List<String> successMessages = new ArrayList();
        successMessages.add(messageSource.getMessage("message.profile.save.success", new Object[]{ }, Locale.getDefault()));
        redirectAttributes.addFlashAttribute("successFlashMessages", successMessages);
        return "redirect:/admin/users/self";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/change-password")
    public String changePassword(
            @Validated(User.UserChangePassword.class) @ModelAttribute(ATTRIBUTE_NAME) User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            // SessionStatus lets you clear your SessionAttributes
            SessionStatus sessionStatus){
     
        if(bindingResult.hasErrors()) {
            //put the validation errors in Flash session and redirect to self
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return "redirect:/admin/users/self";
        }
        userService.updatePassword(user);
        sessionStatus.setComplete(); //remove user from session
        
        return "redirect:/admin/users/self";
    }
}
