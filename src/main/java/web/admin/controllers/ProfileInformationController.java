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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/admin/users/self/profile")
@SessionAttributes({ProfileInformationController.ATTRIBUTE_NAME})
public class ProfileInformationController {
    
    private static Logger logger = LoggerFactory.getLogger(ProfileInformationController.class);
    
    public static final String ATTRIBUTE_NAME = "user";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setAllowedFields("username", "email", "fullName");
    }
   
    @RequestMapping(method = RequestMethod.GET)
    public String show(@CurrentUser User currentUser, Model model) {
        User user = userRepository.findOne(currentUser.getId());
        logger.info(user.toString());
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME, user);
        }
        String url = "/admin/users/self/profile";
        return String.format("admin/fragments/user/personal::form-basic(url='%s')",url);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String process(
        // @ModelAttribute will load User from session but also set values from the form post
       @Validated(User.UserUpdate.class) @ModelAttribute(ATTRIBUTE_NAME) User user,
       BindingResult bindingResult, 
       RedirectAttributes redirectAttributes,
       // SessionStatus lets you clear your SessionAttributes
       SessionStatus sessionStatus,
       @CurrentUser User currentUser
    ){
        
        String url = "/admin/users/self/profile";
        
        if(bindingResult.hasErrors()) {
            //put the validation errors in Flash session and redirect to self
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return "redirect:"+url;
        }
        
        userRepository.save(user);
        sessionStatus.setComplete(); //remove user from session
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getFullName());
        currentUser.setFullName(user.getEmail());
        List<String> successMessages = new ArrayList();
        successMessages.add(messageSource.getMessage("message.profile.save.success", new Object[]{ }, Locale.getDefault()));
        redirectAttributes.addFlashAttribute("successFlashMessages", successMessages);
        return "redirect:"+url;
    }
}
