package com.timezone.www.services;

import com.timezone.www.dto.UserRegistrationDto;
import com.timezone.www.model.User;
import com.timezone.www.model.Role;
import com.timezone.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setUserName(registration.getUserName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public User getOne(Long id){
        return userRepository.getOne(id);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUserName(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid UserName");
        }
        return user;
    }

}
