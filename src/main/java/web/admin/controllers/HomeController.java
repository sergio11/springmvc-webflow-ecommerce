/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
