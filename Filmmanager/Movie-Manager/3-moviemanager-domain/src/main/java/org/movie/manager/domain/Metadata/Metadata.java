package org.movie.manager.domain.Metadata;

import org.movie.manager.domain.Movie.MovieID;
import org.movie.manager.domain.Persistable;

public class Metadata implements Persistable {

    private final MetadataID metadataID; //only getFunction()

    private Availability availability;

    private IMDbData IMDbData;

    private Rating ownRating;

    private MovieID movie;

    public Metadata(MetadataID metadataID, Availability availability, IMDbData IMDbData, Rating ownRating, MovieID movie) {
        if(metadataID != null)
            this.metadataID = metadataID;
        else
            this.metadataID = new MetadataID(null);

        this.availability = availability;
        this.IMDbData = IMDbData;
        this.ownRating = ownRating;
        this.movie = movie;
    }

    public MetadataID getMetadataID() {
        return metadataID;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public IMDbData getImbDdata() {
        return IMDbData;
    }

    public void setImbDdata(IMDbData IMDbData) {
        this.IMDbData = IMDbData;
    }

    public Rating getOwnRating() {
        return ownRating;
    }

    public void setOwnRating(Rating ownRating) {
        this.ownRating = ownRating;
    }

    public MovieID getMovie() {
        return movie;
    }

    public void setMovie(MovieID movie) {
        this.movie = movie;
    }

    @Override
    public Object getPrimaryKey() {
        return metadataID.getMetadataID();
    }
}
