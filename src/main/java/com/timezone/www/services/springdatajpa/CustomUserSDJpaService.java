package com.timezone.www.services.springdatajpa;

import com.timezone.www.model.User;
import com.timezone.www.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomUserSDJpaService  {

    private final UserRepository userRepository;

    public CustomUserSDJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(User object) {
        userRepository.delete(object);
    }

    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

}
