package org.movie.manager.domain.Metadata;

import java.util.Objects;

public final class Rating {

    /*
    Own rating:
-   5-Star vs 10-Star Rating: 10-Star Rating more possible,
    more flexibility, 5-Star is basically multiplied by 2 is a 10-Star Rating,
    with IMBD your own rating is also a 10-Star Rating,
    everything speaks for you fixed 10-star rating

    Has to implement as a value object!
     */
    private final int rating;

    public Rating(int rating) {
        if(rating <1 || rating >10){
            if(rating !=-1){ // -1 = not set
                throw new IllegalArgumentException("Rating must be between 1 and 10");
            }
        }
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Rating: " + rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating that = (Rating) o;

        if (rating != that.rating) return false;
        return true;
    }
}
