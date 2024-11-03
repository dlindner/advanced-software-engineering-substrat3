package org.movie.manager.application.Services;

import org.movie.manager.domain.FilmProfessional.FilmProfessional;
import org.movie.manager.domain.FilmProfessional.FilmProfessionalRepository;
import org.movie.manager.domain.Metadata.Metadata;
import org.movie.manager.domain.Metadata.MetadataRepository;
import org.movie.manager.domain.Movie.Movie;
import org.movie.manager.domain.Movie.MovieRepository;
import java.util.ArrayList;

public class MovieEditService {

    private final MovieRepository movieRepository;
    private final MetadataRepository metadataRepository;
    private final FilmProfessionalRepository filmProfessionalRepository;

    public MovieEditService(MovieRepository movieRepository, MetadataRepository metadataRepository, FilmProfessionalRepository filmProfessionalRepository) {
        this.movieRepository = movieRepository;
        this.metadataRepository = metadataRepository;
        this.filmProfessionalRepository = filmProfessionalRepository;
    }

    public void saveNewMovie(Movie movie, Metadata metadata, ArrayList<FilmProfessional> filmProfessionals) {
        if(movieRepository != null) {
            movieRepository.update(movie);
        }
        if(metadataRepository != null) {
            metadataRepository.update(metadata);
        }
        if(filmProfessionals != null) {
            for(FilmProfessional fp : filmProfessionals) {
                filmProfessionalRepository.update(fp);
            }
        }
    }
}
