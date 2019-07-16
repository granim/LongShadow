package com.timezone.www.bootstrap;

import com.timezone.www.model.Client;
import com.timezone.www.model.Coworker;
import com.timezone.www.model.User;
import com.timezone.www.model.Role;
import com.timezone.www.services.ClientService;
import com.timezone.www.services.CoworkerService;
import com.timezone.www.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.TimeZone;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClientService clientService;
    private final CoworkerService coworkerService;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    public DataLoader(ClientService clientService, CoworkerService coworkerService, UserService userService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.coworkerService = coworkerService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User grant = new User();
        grant.setUserName("Grant");
        grant.setRoles(Arrays.asList(new Role("USER")));
        grant.setEmail("grant@gmail.com");
        grant.setPassword(passwordEncoder.encode("123"));

        User userTwo = new User();
        userTwo.setUserName("Havi");
        userTwo.setRoles(Arrays.asList(new Role("USER")));
        userTwo.setEmail("a@gmail.com");
        userTwo.setPassword(passwordEncoder.encode("123"));

        Role role = new Role();
        role.setName("USER");


        Client client1 = new Client();
        client1.setCompanyName("Telemundo");
        client1.setTelephone("123");
        client1.setCity("Tamps");
        client1.setAddress("234 Hulu");
        client1.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        client1.setUser(grant);
        clientService.save(client1);
        grant.addClient(client1);

        /*Client client2 = new Client();
        client1.setCompanyName("Telemundo 343");
        client1.setTelephone("123534545");
        client1.setCity("Tamps 342");
        client1.setAddress("234 Hulu 243322");
        client1.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        client1.setUser(grant);
        clientService.save(client2);
        grant.addClient(client2);
*/
        Coworker james = new Coworker();

        james.setfName("James");
        james.setlName("Hoffa");
        james.setTelephone("4563214654");
        james.setCity("Orlando");
        james.setAddress("15698 Set Into Drive");
        james.setUser(grant);
        coworkerService.save(james);
        grant.addCoworker(james);

        Coworker james2 = new Coworker();

        james.setfName("Dooo");
        james.setlName("Hoffa");
        james.setTelephone("234");
        james.setCity("324243");
        james.setAddress("15698 Set");
        james.setUser(grant);
        coworkerService.save(james2);
        grant.addCoworker(james2);


        System.out.println("Loaded in Data");
    }

}
