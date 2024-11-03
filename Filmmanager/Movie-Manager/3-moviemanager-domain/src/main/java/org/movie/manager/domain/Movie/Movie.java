package org.movie.manager.domain.Movie;
import org.movie.manager.domain.FilmProfessional.FilmProfessionalID;
import org.movie.manager.domain.Metadata.MetadataID;
import org.movie.manager.domain.Persistable;

import java.util.ArrayList;
import java.util.Collection;

public class Movie implements Persistable {

    //A change should be possible, as typing errors can occur -> no final and for each variable getter and setter functions

    /*
    Issue ID:
    - Do IDs belong to the domain layer?
    - No: Has nothing to do with films per se, only with administration
    - Yes: Also interesting if you want to label your own media library. Then ID's also belong to the film. Of course also implement advantages, since it is easy to implement, can still be used later for other storage options. The idea of the program is simple use of the video library. CSV files offer the advantage of being able to be read without a program and by many other programs that support CSV.
    - Decision yes to ID in domain layer! Also for other classes, because of KISS and same structure principle
    */
    private final MovieID movieID; //only getFunction()
    private String titel;
    private String genre;
    private int releaseYear;
    private int runningTimeInMin;

    private MetadataID metadataID;

    private Collection<FilmProfessionalID> directorIDs = new ArrayList<>();
    private Collection<FilmProfessionalID> actorIDs = new ArrayList<>();
    private Collection<FilmProfessionalID> screenwriterIDs = new ArrayList<>();

    public Movie(MovieID movieID, String titel, String genre, int releaseYear, int runningTimeInMin, MetadataID metadataID, Collection<FilmProfessionalID> directorIDs, Collection<FilmProfessionalID> actorIDs, Collection<FilmProfessionalID> screenwriterIDs) {
        if(movieID != null)
            this.movieID = movieID;
        else
            this.movieID = new MovieID(null);

        this.titel = titel;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.runningTimeInMin = runningTimeInMin;
        this.metadataID = metadataID;
        if (directorIDs != null) this.directorIDs = directorIDs;
        if (actorIDs != null) this.actorIDs = actorIDs;
        if (screenwriterIDs != null) this.screenwriterIDs = screenwriterIDs;
    }

    public MovieID getMovieID() {
        return movieID;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRunningTimeInMin() {
        return runningTimeInMin;
    }

    public void setRunningTimeInMin(int runningTimeInMin) {
        this.runningTimeInMin = runningTimeInMin;
    }

    public Collection<FilmProfessionalID> getDirectorIDs() {
        return directorIDs;
    }

    public void addDirectors(FilmProfessionalID director) {
        directorIDs.add(director);
    }

    public Collection<FilmProfessionalID> getActorIDs() {
        return actorIDs;
    }

    public void addActor(FilmProfessionalID actor) {
        actorIDs.add(actor);
    }

    public Collection<FilmProfessionalID> getScreenwriter() {
        return screenwriterIDs;
    }

    public void addScreenwriter(FilmProfessionalID screenwriter) {
        screenwriterIDs.add(screenwriter);
    }

    public MetadataID getMetadataID() {
        return metadataID;
    }

    public void setMetadataID(MetadataID metadataID) {
        this.metadataID = metadataID;
    }

    @Override
    public Object getPrimaryKey() {
        return movieID.getMovieID();
    }
}
