package org.movie.manager.adapters.Mapper;

import org.movie.manager.domain.Movie.Movie;

import java.util.Arrays;
import java.util.StringJoiner;

public class MovieMapper {

    public static enum Header {
        MOVIEID,
        TITEL,
        GENRE,
        RELEASEYEAR,
        METADATA,
        RUNNINGTIMEINMIN,
        DIRECTORS,
        ACTORS,
        SCREENWRITERS;

        private Header() {
        }
    }
    public String[] mapData(Movie movieData) {
        String[] atts = new String[Header.values().length];
        atts[Header.MOVIEID.ordinal()] = String.valueOf(movieData.getMovieID());
        atts[Header.TITEL.ordinal()] = movieData.getTitel();
        atts[Header.GENRE.ordinal()] = movieData.getGenre();
        atts[Header.RELEASEYEAR.ordinal()] = String.valueOf(movieData.getReleaseYear());
        atts[Header.RUNNINGTIMEINMIN.ordinal()] = String.valueOf(movieData.getRunningTimeInMin());
        atts[Header.METADATA.ordinal()] = String.valueOf(movieData.getMetadataID().getMetadataID());

        if(movieData.getDirectorIDs().size() != 0) {
            StringJoiner sjDirector = new StringJoiner(",");
            movieData.getDirectorIDs().forEach((e) -> sjDirector.add(e.getFilmProfessionalID().toString()));
            atts[Header.DIRECTORS.ordinal()] = sjDirector.toString();
        } else {
            atts[Header.DIRECTORS.ordinal()] = " ";
        }

        if(movieData.getActorIDs().size() != 0) {
            StringJoiner sjActor = new StringJoiner(",");
            movieData.getActorIDs().forEach((e) -> sjActor.add(e.getFilmProfessionalID().toString()));
            atts[Header.ACTORS.ordinal()] = sjActor.toString();
        } else {
            atts[Header.ACTORS.ordinal()] = " ";
        }

        if(movieData.getScreenwriter().size() != 0) {
            StringJoiner sjScreenwriter = new StringJoiner(",");
            movieData.getScreenwriter().forEach((e) -> sjScreenwriter.add(e.getFilmProfessionalID().toString()));
            atts[Header.SCREENWRITERS.ordinal()] = sjScreenwriter.toString();
        } else {
            atts[Header.SCREENWRITERS.ordinal()] = " ";
        }
        return atts;
    }

    public static String[] getHeader() {
        return Arrays.stream(Header.values()).map(Enum::name).toArray(String[]::new);
    }
}
