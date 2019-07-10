package com.timezone.www.controllers;

import com.timezone.www.model.User;
import com.timezone.www.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping({"index", "index.html", "/index"})
    public String Index(User user, Model model){
        org.springframework.security.core.userdetails.User loggeduser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User thisUser = userService.findByEmail(loggeduser.getUsername());
        model.addAttribute("user", thisUser);
        return "index";
    }


 /*   @RequestMapping("index")
    public ModelAndView findMessagesForUser(@AuthenticationPrincipal User customUser) {
       ModelAndView mav = new ModelAndView();
       mav.addObject("customUser", customUser);
       return mav;
    }*/

    @RequestMapping("/oops")
    public String oopsHandler(){
        return "oops";
    }

    @GetMapping ("login")
    public String login() {
        return "login";
    }


}
