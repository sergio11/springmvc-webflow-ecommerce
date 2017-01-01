package web.admin.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sergio
 */
@Controller("AdminUserController")
@RequestMapping("/admin/users")
public class UserController {
    
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping("/all")
    public String all(Model model){
        return "admin/dashboard/user/all";
    }
}
