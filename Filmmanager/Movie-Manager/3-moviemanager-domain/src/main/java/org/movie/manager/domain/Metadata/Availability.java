package org.movie.manager.domain.Metadata;

public class Availability {
    private Ownership ownership;
    private String nameOrMedium; //Friend's name, name of Streaming Media (owned) or Cassette, DVD
    private String description; //Quality (Poor, Good, Very Good), Resolution, Available for a limited time only

    public Availability(Ownership ownership, String nameOrMedium, String description) {
        this.ownership = ownership;
        this.nameOrMedium = nameOrMedium;
        this.description = description;
    }

    public Ownership getOwnership() {
        return ownership;
    }

    public void setOwnership(Ownership ownership) {
        this.ownership = ownership;
    }

    public String getNameOrMedium() {
        return nameOrMedium;
    }

    public void setNameOrMedium(String nameOrMedium) {
        this.nameOrMedium = nameOrMedium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
