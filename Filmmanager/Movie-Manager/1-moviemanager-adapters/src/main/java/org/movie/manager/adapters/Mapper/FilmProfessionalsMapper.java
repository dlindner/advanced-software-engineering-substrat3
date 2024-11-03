package org.movie.manager.adapters.Mapper;

import org.movie.manager.domain.FilmProfessional.FilmProfessional;

import java.util.Arrays;
import java.util.StringJoiner;

public class FilmProfessionalsMapper {

    public static enum Header {
        FILMPROFESSIONALID,
        FIRSTNAME,
        SECONDNAME,
        BIOGRAPHY,

        MOVIES;

        private Header() {
        }
    }

    public String[] mapData(FilmProfessional filmProfessionalData) {
        String[] atts = new String[Header.values().length];
        atts[Header.FILMPROFESSIONALID.ordinal()] = String.valueOf(filmProfessionalData.getFilmProfessionalID());
        atts[Header.FIRSTNAME.ordinal()] = filmProfessionalData.getFirstName();
        atts[Header.SECONDNAME.ordinal()] = filmProfessionalData.getSecondName();
        atts[Header.BIOGRAPHY.ordinal()] = filmProfessionalData.getBiography();

        if(filmProfessionalData.getContributeMovies().size() != 0) {
            StringJoiner sjMovie = new StringJoiner(",");
            filmProfessionalData.getContributeMovies().forEach((e) -> sjMovie.add(e.getMovieID().toString()));
            atts[Header.MOVIES.ordinal()] = sjMovie.toString();
        } else {
            atts[Header.MOVIES.ordinal()] = " ";
        }
        return atts;
    }

    public static String[] getHeader() {
        return Arrays.stream(Header.values()).map(Enum::name).toArray(String[]::new);
    }
}
