package com.oshovskii.movie.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("RoleRepository Test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("getByName() +" +
            "with valid role name " +
            "should return Role test")
    void getByName_validRoleName_shouldReturnRole() {
        // Config
        val existingRoleName = "ROLE_TEST_1";

        // Call
        val actualRole = roleRepository.getByName(existingRoleName);

        // Verify
        assertEquals(existingRoleName, actualRole.getName());
    }
}
