package com.timezone.www.services;

import com.timezone.www.model.Coworker;

import java.util.List;

public interface CoworkerService extends CrudService<Coworker, Long>{

    Coworker findBylName(String lName);

    List<Coworker> findAllBylNameLike(String lName);

    List<Coworker> findAllByUserId(Long userId);



}
