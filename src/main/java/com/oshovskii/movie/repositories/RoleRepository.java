package com.oshovskii.movie.repositories;

import com.oshovskii.movie.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role getByName(String name);
}

