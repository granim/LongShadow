package com.timezone.www.controllers;

import com.timezone.www.model.Coworker;
import com.timezone.www.model.User;
import com.timezone.www.services.CoworkerService;
import com.timezone.www.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users/{userEmail}")
public class CoworkerController {

    private static final String VIEWS_COWORKER_CREATE_OR_UPDATE_FORM = "coworkers/createOrUpdateCoworkerForm";
    private final UserService userService;
    private final CoworkerService coworkerService;
    private Long userIdToHold;

    public CoworkerController(UserService userService, CoworkerService coworkerService) {
        this.userService = userService;
        this.coworkerService = coworkerService;
    }


    @ModelAttribute("user")
    public User findBaseUser(@PathVariable("userEmail") String userEmail) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userService.findByEmail(user.getUsername());

        Long loggedUserId = loggedUser.getId();
        userIdToHold = loggedUserId;
        return userService.findById(loggedUserId);
    }

    @InitBinder("user")
    public void initBaseUserBinder(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("user")
    public void initCustomUserBinder(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("userEmail");
    }


    @RequestMapping("/coworkers/find")
    public String findCoworker(Model model){
        model.addAttribute("coworker", Coworker.builder().build());
        return  "coworkers/findCoworkers";

    }

    @GetMapping("/coworkers")
    public String processFindCoworkerForm(Coworker coworker, BindingResult result, User user, Model model) {
        if(coworker.getlName() == null) {
            coworker.setlName("");
        }
        List<Coworker> coworkersByUserId = coworkerService.findAllByUserId(user.getId());
        if(coworkersByUserId.isEmpty()) {
            result.rejectValue("lName", "notFound", "not found");
            return "coworkers/findCoworkers";
        } else if (coworkersByUserId.size() == 1) {
            coworker = coworkersByUserId.get(0);
            return "redirect:/users/" + user.getEmail() + "/coworkers/" + coworker.getId();
        } else {
            model.addAttribute("selections", coworkersByUserId);
            return "coworkers/coworkerList";
        }
    }

    @GetMapping("/coworkers/{coworkerId}")
    public ModelAndView showCustomUser(@PathVariable("coworkerId") Long coworkerId){
        ModelAndView mav = new ModelAndView("coworkers/coworkerDetails");
        mav.addObject(coworkerService.findById(coworkerId));
        return mav;
    }

    @GetMapping("/coworkers/new")
    public String initCreationForm(User user, ModelMap model){
        Coworker coworker = new Coworker();
        user.addCoworker(coworker);
        model.put("coworker", coworker);
        return VIEWS_COWORKER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/coworkers/new")
    public String processCreationForm(User user, @Valid Coworker coworker, BindingResult result, Model model) {
        if (StringUtils.hasLength(coworker.getfName()) && coworker.isNew() && user.getCoworker(coworker.getfName(), true) != null){
            result.rejectValue("fName", "duplicate", "already exists");
        }

        if(user.getId() == null) {
            user.setid(userIdToHold);
        }
        user.addCoworker(coworker);
        if(result.hasErrors()) {
            model.addAttribute("coworker", coworker);
            return VIEWS_COWORKER_CREATE_OR_UPDATE_FORM;
        } else {
            coworkerService.save(coworker);
            return "redirect:/users/{userEmail}";
        }
    }

    @GetMapping("/coworkers/{coworkerId}/edit")
    public String initUpdateForm(@PathVariable Long coworkerId, Model model){
        model.addAttribute("coworker" , coworkerService.findById(coworkerId));
        return VIEWS_COWORKER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/coworkers/{coworkerId}/edit")
    public String processUpdateForm(@Valid Coworker coworker, BindingResult result, User user, Model model) {
        if(result.hasErrors()) {
            coworker.setUser(user);
            model.addAttribute("coworker", coworker);
            return VIEWS_COWORKER_CREATE_OR_UPDATE_FORM;
        } else {
            user.addCoworker(coworker);
            coworkerService.save(coworker);
            return "redirect:/users/" + user.getEmail();
        }
    }
}