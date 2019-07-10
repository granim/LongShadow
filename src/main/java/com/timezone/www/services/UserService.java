package com.timezone.www.services;

import com.timezone.www.dto.UserRegistrationDto;
import com.timezone.www.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String username);

    User findByEmail(String email);

    User findById(Long id);

    User save(User user);

    User save(UserRegistrationDto registration);

    User update(User user);

    User getOne(Long Id);
}
