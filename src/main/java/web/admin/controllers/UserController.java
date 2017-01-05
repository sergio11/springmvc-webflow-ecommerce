package web.admin.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.models.User;
import web.services.UserService;

/**
 * @author sergio
 */
@Controller("AdminUserController")
@RequestMapping("/admin/users")
@SessionAttributes({ProductController.ATTRIBUTE_NAME})
public class UserController {
    
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    
    public static final String ATTRIBUTE_NAME = "user";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;
    
    @Autowired
    private UserService userService;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    
    @GetMapping("/all")
    public String all(Model model){
        return "admin/dashboard/user/all";
    }
    
    @GetMapping("/create")
    public String create(Model model){
        if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME,  new User());
        }
        return "admin/dashboard/user/create";
    }
    
    @PostMapping("/save")
    public String create(
            @RequestPart("avatarfile") MultipartFile avatarFile,
            @Validated(User.UserCreation.class) @ModelAttribute(ATTRIBUTE_NAME) User user, 
            BindingResult bindingResult,
            @RequestParam(value = "continueEditing", required = false, defaultValue = "false") boolean continueEditing,
            RedirectAttributes model,
            // SessionStatus lets you clear your SessionAttributes
            SessionStatus sessionStatus,
            HttpServletRequest request){
        
        if (bindingResult.hasErrors()) {
            model.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            String referer = request.getHeader("Referer");
            return "redirect:"+referer;
        }
        
        if(avatarFile != null && !avatarFile.isEmpty()){
            userService.create(user, avatarFile);
        } else {
            userService.create(user);
        }
        
        sessionStatus.setComplete(); //remove user from session
        List<String> successMessages = new ArrayList();
        successMessages.add(messageSource.getMessage("message.user.save.success", new Object[]{user.getFullName()}, Locale.getDefault()));
        model.addFlashAttribute("successFlashMessages", successMessages);
        String redirectTo;
        if(continueEditing){
            model.addAttribute("userId", user.getId());
            redirectTo = "redirect:/admin/users/edit/{productId}";
        }else{
            redirectTo = "redirect:/admin/users/all";
        }
        return redirectTo; 
    }
}
