package org.movie.manager.domain.Metadata;

import java.util.Objects;
import java.util.UUID;

public final class MetadataID {
    private UUID metadataID;

    public MetadataID(UUID metadataID) {
        if(metadataID != null)
            this.metadataID = metadataID;
        else
            this.metadataID = UUID.randomUUID();
    }

    public UUID getMetadataID() {
        return metadataID;
    }

    @Override
    public String toString() {
        return metadataID.toString(); // todo: implement this with name like: <Metadata ID: metadataID>
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadataID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataID that = (MetadataID) o;
        return metadataID.equals(that.metadataID);
    }
}
