INSERT INTO roles (name)
VALUES ('ROLE_TEST_1'),
       ('ROLE_TEST_2');

INSERT INTO users (username, password, email)
VALUES ('user_1', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user_1@gmail.com'),
       ('user_2', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user_2@gmail.com'),
       ('user_3', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user_3@gmail.com'),
       ('user_4', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user_4@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 1);

INSERT INTO movies (kinopoisk_id, title, poster_path)
VALUES ('kinopoisk_id_test_1', 'title_test_1', 'https://st.kp.yandex.net/test1'),
       ('kinopoisk_id_test_2', 'title_test_2', 'https://st.kp.yandex.net/test2');

INSERT INTO users_movies (user_id, movie_id)
VALUES (4, 1);
