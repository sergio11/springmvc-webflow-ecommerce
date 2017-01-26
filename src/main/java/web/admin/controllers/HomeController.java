package web.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author sergio
 */
@Controller("AdminHomeController")
public class HomeController {
    @GetMapping("/admin/home")
    public String show(){
        return "admin/dashboard/index";
    }
}
