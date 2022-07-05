package com.oshovskii.movie.repositories;

import com.oshovskii.movie.model.Role;
import com.oshovskii.movie.model.User;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("UserRepository Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findByUsername() +" +
            "with valid username " +
            "should return Optional<User> test")
    void findByUsername_validUsername_shouldReturnOptionalUser() {
        // Config
        val existingUsername = "user_1";
        val existingUser = new User(
                1L,
                existingUsername,
                null,
                "$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i",
                "user_1@gmail.com",
                Set.of(),
                Set.of(new Role(1L, "ROLE_TEST_1"), new Role(2L, "ROLE_TEST_2"))
        );

        // Call
        val actualUser = userRepository.findByUsername(existingUsername);

        // Verify
        assertThat(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(existingUser);
    }

    @Test
    @DisplayName("findByEmail() +" +
            "with valid email " +
            "should return Optional<User> test")
    void findByEmail_validEmail_shouldReturnOptionalUser() {
        // Config
        val existingEmail = "user_2@gmail.com";

        val existingUser = new User(
                2L,
                "user_2",
                null,
                "$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i",
                existingEmail,
                Set.of(),
                Set.of(new Role(1L, "ROLE_TEST_1"))
        );

        // Call
        val actualUser = userRepository.findByEmail(existingEmail);

        // Verify
        assertThat(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(existingUser);
    }

    @Test
    @DisplayName("findByUsernameAndPassword() +" +
            "with valid email and password " +
            "should return Optional<User> test")
    void findByUsernameAndPassword_validUsernameAndPassword_shouldReturnOptionalUser() {
        // Config
        val existingUsername = "user_3";
        val existingPassword = "$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i";

        val existingUser = new User(
                3L,
                existingUsername,
                null,
                existingPassword,
                "user_3@gmail.com",
                Set.of(),
                Set.of(new Role(1L, "ROLE_TEST_1"))
        );

        // Call
        val actualUser = userRepository.findByUsernameAndPassword(existingUsername, existingPassword);

        // Verify
        assertThat(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(existingUser);
    }
}
