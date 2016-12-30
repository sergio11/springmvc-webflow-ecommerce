package web.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author sergio
 */
@Controller("FrontendHomeController")
public class HomeController {
    @GetMapping(value={"/home", "/"})
    public String index(){
        return "frontend/index";
    }
}
