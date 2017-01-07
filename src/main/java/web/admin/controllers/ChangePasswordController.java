package web.admin.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.User;
import security.CurrentUser;
import web.services.UserService;

/**
 * @author sergio
 */
@Controller
@RequestMapping("/admin/users/self/change-password")
@SessionAttributes({ProfileInformationController.ATTRIBUTE_NAME})
public class ChangePasswordController {
    
    private static Logger logger = LoggerFactory.getLogger(ChangePasswordController.class);
    
    public static final String ATTRIBUTE_NAME = "user";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private UserService userService;
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setAllowedFields("currentClearPassword", "passwordClear", "confirmPassword");
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String show(@CurrentUser User user, Model model) {
        logger.info(user.toString());
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME, user);
        }
        return "admin/fragments/user/password::form";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String changePassword(
            @Validated(User.UserChangePassword.class) @ModelAttribute(ATTRIBUTE_NAME) User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            // SessionStatus lets you clear your SessionAttributes
            SessionStatus sessionStatus){
        
        String url = "/admin/users/self/change-password";
     
        if(bindingResult.hasErrors()) {
            //put the validation errors in Flash session and redirect to self
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return "redirect:"+url;
        }
        
        userService.updatePassword(user);
        sessionStatus.setComplete(); //remove user from session
        return "redirect:"+url;
    }
}
