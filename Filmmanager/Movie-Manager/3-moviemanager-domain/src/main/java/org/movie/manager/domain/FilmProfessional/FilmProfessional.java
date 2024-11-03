package org.movie.manager.domain.FilmProfessional;

import org.movie.manager.domain.Movie.MovieID;
import org.movie.manager.domain.Persistable;

import java.util.ArrayList;
import java.util.Collection;

public class FilmProfessional implements Persistable {

    private final FilmProfessionalID filmProfessionalID; //only getFunction()
    private final String firstName;
    private String secondName;
    private String biography; // Anything that might be interesting about a person: e.g. birthday, birthCountry, NumberOscars, etc.

    private Collection<MovieID> contributeMovies = new ArrayList<>();

    public FilmProfessional(FilmProfessionalID filmProfessionalID, String firstName, String secondName, String biography, Collection<MovieID> movies) {
        if(filmProfessionalID != null)
            this.filmProfessionalID = filmProfessionalID;
        else
            this.filmProfessionalID = new FilmProfessionalID(null);

        this.firstName = firstName;
        this.secondName = secondName;
        this.biography = biography;
        if (movies != null) this.contributeMovies = movies;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public FilmProfessionalID getFilmProfessionalID() {
        return filmProfessionalID;
    }

    public void addMovie(MovieID movie) {
        contributeMovies.add(movie);
    }

    public Collection<MovieID> getContributeMovies() {
        return contributeMovies;
    }

    @Override
    public Object getPrimaryKey() {
        return filmProfessionalID.getFilmProfessionalID();
    }
}
