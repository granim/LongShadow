package com.timezone.www.repository;

import com.timezone.www.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findAllById(Long id);


}
