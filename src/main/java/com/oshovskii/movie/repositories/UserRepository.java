package com.oshovskii.movie.repositories;

import com.oshovskii.movie.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.favorites WHERE u.id = :id")
    Optional<User> findByIdFetch(Long id);
}
