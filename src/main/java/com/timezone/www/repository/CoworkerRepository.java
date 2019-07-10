package com.timezone.www.repository;

import com.timezone.www.model.Coworker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CoworkerRepository extends CrudRepository<Coworker, Long> {

    Coworker findBylName(String lName);

    List<Coworker> findAllBylNameLike(String lName);

    List<Coworker> findAllByUser_Id(Long userId);


}
