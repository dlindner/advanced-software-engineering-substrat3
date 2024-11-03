package org.movie.manager.domain.Metadata;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface MetadataRepository {

    Collection<Metadata> getAllMetadata();

    Optional<Metadata> getMetadata(UUID metadataID);

    void update(Metadata metadata);
}
