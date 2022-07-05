package com.oshovskii.movie.service.implementations;

import com.oshovskii.movie.model.Role;
import com.oshovskii.movie.repositories.RoleRepository;
import com.oshovskii.movie.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(name);
    }
}
