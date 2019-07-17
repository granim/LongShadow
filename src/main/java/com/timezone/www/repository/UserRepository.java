package com.timezone.www.repository;

import com.timezone.www.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    User findByEmail(String email);

    Optional<User> findById(Long userId);

    User getOne(Long userId);


}
