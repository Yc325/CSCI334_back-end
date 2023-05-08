package com.csci334.ConferenceMagment.repository;

import com.csci334.ConferenceMagment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.id in(select d.user.id from Authority d where d.authority = 'ROLE_REVIEWER')")
    Set<User> findByReviwer();
}
