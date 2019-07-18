package com.timezone.www.controllers;

import com.timezone.www.model.User;
import com.timezone.www.repository.UserRepository;
import com.timezone.www.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private static final String VIEWS_CUSTOMUSER_CREATE_OR_UPDATE_FORM = "users/createOrUpdateUserForm";

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findUser(Model model){
        model.addAttribute("customUser", User.builder().build());
        return "users/findUsers";
    }

/*    @GetMapping("/{userEmail}")
    public ModelAndView showBaseUser(@PathVariable("userEmail") String email) {
        ModelAndView mav = new ModelAndView("users/userDetails");
        mav.addObject(userService.findByEmail(email));
        return mav;
    }*/

    @GetMapping("/{userEmail}")
    public ModelAndView showUser(@PathVariable("userEmail")String userEmail){
        ModelAndView mav = new ModelAndView("users/userDetails");
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userService.findByEmail(user.getUsername());
        String loggedUserEmail = loggedUser.getEmail();
        mav.addObject(userService.findByEmail(loggedUserEmail));
        return mav;
    }

    @GetMapping("/{userEmail}/edit")
    public String initUpdateUserForm(@PathVariable String userEmail, Model model){
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userService.findByEmail(currentUser.getUsername());
        model.addAttribute(userService.findByEmail(loggedUser.getEmail()));
        return VIEWS_CUSTOMUSER_CREATE_OR_UPDATE_FORM;
    }

    //Find the currently logged in user and update that users information.
    @PostMapping("/{userEmail}/edit")
    public String processUpdateForm(@Valid User user, BindingResult result, @PathVariable String userEmail){
        if(result.hasErrors()){
            return VIEWS_CUSTOMUSER_CREATE_OR_UPDATE_FORM;
        } else {
           org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user1 = userService.findByEmail(loggedUser.getUsername());
            Long id = user1.getId();
        /*     User userToUpdate = userService.getOne(id);
           userToUpdate.setEmail(userEmail);
            userService.save(user);
            return "redirect:/users/" + user.getEmail() + "/edit";*/
       User userToUpdate = userRepository.getOne(id);
       userToUpdate.setEmail(userEmail);
       userRepository.save(userToUpdate);
            return "redirect:/users/" + user.getEmail() + "/edit";
        }
    }




    @GetMapping("/new")
    public String view(Model model) {
        model.addAttribute("customUser", User.builder().build());
        return VIEWS_CUSTOMUSER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            return VIEWS_CUSTOMUSER_CREATE_OR_UPDATE_FORM;
        } else {
            User savedUser = userService.save(user);
            return "redirect:/users/" + savedUser.getEmail();
        }
    }

}
