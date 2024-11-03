package org.movie.manager.domain.Movie;

import java.util.Objects;
import java.util.UUID;

public class MovieID {
    private UUID movieID;

    public MovieID(UUID movieID) {
        if(movieID != null)
            this.movieID = movieID;
        else
            this.movieID = UUID.randomUUID();
    }

    public UUID getMovieID() {
        return movieID;
    }

    @Override
    public String toString() {
        return movieID.toString(); // todo: implement this with name like: <Movie ID: movieID>
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieID that = (MovieID) o;
        return movieID.equals(that.movieID);
    }
}
