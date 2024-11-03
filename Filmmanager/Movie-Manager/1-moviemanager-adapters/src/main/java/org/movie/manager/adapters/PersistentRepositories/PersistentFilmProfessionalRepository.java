package org.movie.manager.adapters.PersistentRepositories;

import org.movie.manager.adapters.Database;
import org.movie.manager.adapters.EntityManager;
import org.movie.manager.adapters.Mapper.FilmProfessionalsMapper;
import org.movie.manager.domain.FilmProfessional.FilmProfessional;
import org.movie.manager.domain.FilmProfessional.FilmProfessionalRepository;
import org.movie.manager.domain.Movie.MovieID;

import java.util.*;

public class PersistentFilmProfessionalRepository implements FilmProfessionalRepository {

    private final EntityManager entityManager;

    private Database csvDB;


    public PersistentFilmProfessionalRepository(EntityManager entityManager, Database csvDB) {
        this.entityManager = entityManager;
        this.csvDB = csvDB;
    }

    @Override
    public Collection<FilmProfessional> getAllFilmProfessionals() {
        return entityManager.find(FilmProfessional.class);
    }

    @Override
    public Optional<FilmProfessional> getFilmProfessional(UUID filmProfessionalID) {
        return Optional.of((FilmProfessional)entityManager.find(FilmProfessional.class, filmProfessionalID));
    }

    @Override
    public Collection<FilmProfessional> getFilmProfessionalsOfMovie(UUID movieID) {
        Collection<FilmProfessional> allFilmProfessionals = entityManager.find(FilmProfessional.class);
        Collection<FilmProfessional> matchingFilmProfessionals = new ArrayList<>();
        for(FilmProfessional fp : allFilmProfessionals) {
            for(MovieID mID : fp.getContributeMovies()) {
                if(mID.getMovieID().compareTo(movieID) == 0) {
                    matchingFilmProfessionals.add(fp);
                }
            }
        }
        return matchingFilmProfessionals;
    }

    @Override
    public void update(FilmProfessional filmProfessional) {
        FilmProfessional filmProfessionalAlt = entityManager.find(filmProfessional.getPrimaryKey());
        if(filmProfessionalAlt != null){
            entityManager.remove(filmProfessionalAlt);
        }

        try {
            entityManager.persist(filmProfessional);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Save
        FilmProfessionalsMapper csvFilmProfessionalMapper = new FilmProfessionalsMapper();
        List<Object[]> csvDataFilmProfessional = new ArrayList<>();
        List<FilmProfessional> alleFilmProfessional = this.entityManager.find(FilmProfessional.class);
        alleFilmProfessional.forEach( e -> csvDataFilmProfessional.add( (csvFilmProfessionalMapper.mapData(e) )));
        csvDB.saveData("FilmProfessional.csv", csvDataFilmProfessional, FilmProfessionalsMapper.getHeader());
    }
}
