package com.oshovskii.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.movie.dto.movies.MovieDto;
import com.oshovskii.movie.service.MovieService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static com.oshovskii.movie.factory.TestDtoFactory.createMovieDto;
import static com.oshovskii.movie.factory.TestEntityFactory.createMovie;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@Import(MovieControllerImpl.class)
@DisplayName("MovieController test")
class MovieControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapperMock;

    @MockBean
    private MovieService movieServiceMock;

    @Test
    @DisplayName("findAllMovies() " +
            "with valid page and size " +
            "should return Page<MovieDto> test")
    void findAllMovies_validPageAndSize_shouldReturnPageMovieDto() throws Exception {
        // Config
        val sourcePage = 1;
        val sourceSize = 2;
        val pageable = PageRequest.of(sourcePage, sourceSize);

        val sourceMovie1 = createMovie(1);
        val sourceMovie2 = createMovie(2);

        val targetMovieDto1 = createMovieDto(1);
        val targetMovieDto2 = createMovieDto(2);

        val content = List.of(sourceMovie1, sourceMovie2);
        val sourcePageMovies =  new PageImpl<>(content, pageable, content.size());

        val contentAfterMapToDto = List.of(targetMovieDto1, targetMovieDto2);
        val targetPageMovieDtos =  new PageImpl<>(contentAfterMapToDto, pageable, content.size());

        when(movieServiceMock.findAll(sourcePage, sourceSize)).thenReturn(sourcePageMovies);

        when(modelMapperMock.map(sourceMovie1, MovieDto.class)).thenReturn(targetMovieDto1);

        when(modelMapperMock.map(sourceMovie2, MovieDto.class)).thenReturn(targetMovieDto2);

        val targetJson = objectMapper.writeValueAsString(targetPageMovieDtos);

        val requestParams = new LinkedMultiValueMap<String, String>();
        requestParams.add("p", "1");
        requestParams.add("s", "2");

        // Call and Verify
        mockMvc.perform(get("/api/v1/movies")
                        .params(requestParams))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

        verify(movieServiceMock).findAll(sourcePage, sourceSize);
        verify(modelMapperMock).map(sourceMovie1, MovieDto.class);
        verify(modelMapperMock).map(sourceMovie2, MovieDto.class);
    }

    @Test
    @DisplayName("findAllMoviesNotFavorite() " +
            "with valid request param loaderType and header User-Id" +
            "should return List<MovieDto> test")
    void findAllMoviesNotFavorite_validLoaderTypeAndHeaderUserId_shouldReturnListMovieDto() throws Exception {
        // Config
        val headerUserId = "1";
        val loaderType = "sql";

        val targetMovie1 = createMovie(1);
        val targetMovie2 = createMovie(2);

        val targetMovieDto1 = createMovieDto(1);
        val targetMovieDto2 = createMovieDto(2);

        val targetListMovie = List.of(targetMovie1, targetMovie2);
        val targetListMovieDto = List.of(targetMovieDto1, targetMovieDto2);

        when(movieServiceMock.findAllMoviesNotFavorite(headerUserId)).thenReturn(targetListMovie);

        when(movieServiceMock.findAllMoviesNotFavoriteCache(headerUserId)).thenReturn(targetListMovie);

        when(modelMapperMock.map(targetMovie1, MovieDto.class)).thenReturn(targetMovieDto1);

        when(modelMapperMock.map(targetMovie2, MovieDto.class)).thenReturn(targetMovieDto2);

        val targetJson = objectMapper.writeValueAsString(targetListMovieDto);

        // Call and Verify
        mockMvc.perform(get("/api/v1/movies/favorite/not")
                        .param("loaderType", loaderType)
                        .header("User-Id", headerUserId))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

        verify(movieServiceMock).findAllMoviesNotFavorite(headerUserId);
        verify(modelMapperMock).map(targetMovie1, MovieDto.class);
        verify(modelMapperMock).map(targetMovie2, MovieDto.class);
    }
}
