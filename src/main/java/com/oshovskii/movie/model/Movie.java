package com.oshovskii.movie.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kinopoisk_id")
    private String kinopoiskId;

    @Column(name = "title")
    private String title;

    @Column(name = "poster_path")
    private String posterPath;
}
