package com.exomat.mab.repository;

import com.exomat.mab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAll();

    Optional<User> findByUsername(String  username);

    boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);
}
