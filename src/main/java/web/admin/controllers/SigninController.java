package web.admin.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author sergio
 */
@Controller("AdminSigninController")
@RequestMapping("/admin")
public class SigninController {
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer != null && referrer.startsWith("/admin")) {
            request.getSession().setAttribute("url_prior_login", referrer);
        }
        return "admin/account/login";
    }
}
