package com.oshovskii.movie.dto.movies;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Docs {
    private String id;

    private String year;

    private Rating rating;

    private ExternalId externalId;

    private String description;

    private String shortDescription;

    private String type;

    private String movieLength;

    private Collection<Names> names;

    private String name;

    private String enName;

    private Votes votes;

    private String alternativeName;

    private Poster poster;

    public Docs(String alternativeName, Poster poster) {
        this.alternativeName = alternativeName;
        this.poster = poster;
    }
}
