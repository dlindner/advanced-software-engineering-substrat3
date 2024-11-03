package org.movie.manager.domain.FilmProfessional;

import java.util.Objects;
import java.util.UUID;

public final class FilmProfessionalID {
    private UUID filmProfessionalID;

    public FilmProfessionalID(UUID filmProfessionalID) {
        if(filmProfessionalID != null)
            this.filmProfessionalID = filmProfessionalID;
        else
            this.filmProfessionalID = UUID.randomUUID();
    }

    public UUID getFilmProfessionalID() {
        return filmProfessionalID;
    }

    @Override
    public String toString() {
        return filmProfessionalID.toString(); // todo: implement this with name like: <Film Professional ID: filmProfessionalID>
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmProfessionalID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmProfessionalID that = (FilmProfessionalID) o;
        return filmProfessionalID.equals(that.filmProfessionalID);
    }
}
