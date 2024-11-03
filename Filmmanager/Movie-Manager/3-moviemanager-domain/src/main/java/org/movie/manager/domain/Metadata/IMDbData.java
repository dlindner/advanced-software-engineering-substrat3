package org.movie.manager.domain.Metadata;

public class IMDbData {

    private String iMDBID;
    /*
    Must be in its own aggregate, not part of a film.
    There can be a rating for the film, but it is not necessarily part of it.
    Especially if the film is new, this always has no rating.
    */

    private double iMDbRating;

    // METASCORE is a weighted average of reviews from top critics and publications for a given movie
    private int metascore;


    public IMDbData(String iMDBID, double iMDbRating, int metascore) {
        this.iMDBID = iMDBID;
        this.iMDbRating = iMDbRating;
        this.metascore = metascore;
    }

    public String getiMDBID() {
        return iMDBID;
    }

    public void setiMDBID(String iMDBID) {
        this.iMDBID = iMDBID;
    }

    public double getiMDbRating() {
        return iMDbRating;
    }

    public void setiMDbRating(double iMDbRating) {
        this.iMDbRating = iMDbRating;
    }

    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }
}
