package com.timezone.www.services.springdatajpa;


import com.timezone.www.model.Coworker;
import com.timezone.www.model.User;
import com.timezone.www.repository.CoworkerRepository;
import com.timezone.www.repository.UserRepository;
import com.timezone.www.services.CoworkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class CoworkerSDJpaService implements CoworkerService {

    private final CoworkerRepository coworkerRepository;
    private final UserRepository userRepository;

    public CoworkerSDJpaService(CoworkerRepository coworkerRepository, UserRepository userRepository) {
        this.coworkerRepository = coworkerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Coworker findBylName(String lName) {
        return coworkerRepository.findBylName(lName);
    }

    @Override
    public List<Coworker> findAllBylNameLike(String lName) {
       return coworkerRepository.findAllBylNameLike(lName);
    }

    @Override
    public List<Coworker> findAllByUserId(Long userId) {
        return coworkerRepository.findAllByUser_Id(userId);
    }


    @Override
    public Set<Coworker> findAll() {
        Set<Coworker> coworkers= new HashSet<>();
        coworkerRepository.findAll().forEach(coworkers::add);
        return coworkers;
    }

    @Override
    public Coworker findById(Long aLong) {
        return coworkerRepository.findById(aLong).orElse(null);
    }

    @Override
    @Transactional
    public Coworker save(Coworker object) {
        return coworkerRepository.save(object);
    }

    @Override
    public void delete(Coworker object) {
           coworkerRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
         coworkerRepository.deleteById(aLong);
    }
}
