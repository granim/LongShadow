package com.timezone.www.controllers;

import com.timezone.www.model.Client;
import com.timezone.www.model.User;
import com.timezone.www.services.ClientService;
import com.timezone.www.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
public class ClientController {

    private static final String VIEWS_CLIENT_CREATE_OR_UPDATE_FORM = "clients/createOrUpdateClientForm.html";
    private final UserService userService;
    private final ClientService clientService;



    public ClientController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    //May have to change loggedUser findByEmail to findById
    @ModelAttribute("user")
    public User findUser(@PathVariable("userEmail") String userEmail) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userService.findByEmail(user.getUsername());
        String loggedUserEmail = loggedUser.getEmail();
        return userService.findByEmail(loggedUserEmail);
    }

    @InitBinder("user")
    public void initBaseUserBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("user")
    public void initCustomUserBinder(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("userEmail");
    }

    @RequestMapping("/clients/find")
    public String findClient(Model model){
        model.addAttribute("client", Client.builder().build());
        return "clients/findClients";
    }

    @GetMapping("/clients")
    public String processFindClientForm(Client client, BindingResult result, Model model, User user) {
        if(client.getCompanyName() == null) {
            client.setCompanyName("");
        }
        List<Client> clientsByUserId = clientService.findAllByUserId(user.getId());
        List<Client> clientResults = clientService.findAllByCompanyNameLike("%" + client.getCompanyName() + "%");
        if(clientsByUserId.isEmpty()) {
            result.rejectValue("companyName", "notFound", "not found");
            return "clients/findClients";
        } else if (clientsByUserId.size() == 1) {
            client = clientsByUserId.get(0);
            return "redirect:/users/"  + user.getEmail() + "/clients/" + client.getId();
        } else {
            model.addAttribute("selections", clientsByUserId);
            return "clients/clientList";
        }

    }

    @GetMapping("/clients/{clientId}")
    public ModelAndView showBaseUser(@PathVariable("clientId") Long clientId) {
        ModelAndView mav = new ModelAndView("clients/clientDetails");
        mav.addObject(clientService.findById(clientId));
        return mav;
    }

    @GetMapping("/clients/new")
    public String initCreationForm(User user, ModelMap model){
        Client client = new Client();
        user.addClient(client);
        model.put("client", client);
        return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/clients/new")
    @Transactional
    public String processCreationForm(User user, @Valid Client client, BindingResult result, Model model){
        if(StringUtils.hasLength(client.getCompanyName()) && client.isNew() && user.getBaseClient(client.getCompanyName(), true) != null){
            result.rejectValue("companyName", "duplicate", "already exists");
        }

        user.addClient(client);
        if(result.hasErrors()){
            model.addAttribute("client", client);
            return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
        } else {
            clientService.save(client);
            return "redirect:/users/{userEmail}";
        }
    }

    @GetMapping("/clients/{clientId}/edit")
    public String initUpdateForm(@PathVariable Long clientId, Model model){
        model.addAttribute("client", clientService.findById(clientId));
        return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("clients/{clientId}/edit")
    public String processUpdateForm(@Valid Client client, BindingResult result, User user, Model model) {
        if(result.hasErrors()) {
            client.setUser(user);
            model.addAttribute("client", client);
            return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
        }
        user.addClient(client);
        clientService.save(client);
        return "redirect:/users/" + user.getEmail();
    }

}
