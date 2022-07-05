package com.oshovskii.movie.factory;

import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.model.Role;
import com.oshovskii.movie.model.User;

import java.util.HashSet;

public class TestEntityFactory {

    public static User createUser(int index) {

        return new User(
                (long) index,
                "username",
                "name_test_" + index,
                "password_test_" + index,
                "email_test@gmail_" + index,
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public static Movie createMovie(int index) {
        return new Movie(
                (long) index,
                "kinopoisk_id_" + index,
                "title_test_" + index,
                "poster_path_" + index
        );
    }

    public static Role createRole(int index) {
        return new Role(
                (long) index,
                "name_test_" + index
        );
    }
}
