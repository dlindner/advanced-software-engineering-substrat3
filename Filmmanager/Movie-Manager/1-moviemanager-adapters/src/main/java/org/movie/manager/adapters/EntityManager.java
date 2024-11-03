package org.movie.manager.adapters;
import org.movie.manager.domain.FilmProfessional.FilmProfessional;
import org.movie.manager.domain.Metadata.Metadata;
import org.movie.manager.domain.Movie.Movie;
import org.movie.manager.domain.Persistable;

import java.util.List;

public interface EntityManager {
    <T extends Persistable> boolean contains(T element);

    <T extends Persistable> void persist(T element) throws Exception;
    <T extends Persistable> void remove(T element);

    Persistable find(Class<?> c, Object key);

    <T extends Persistable> List<T> find(Class<?> c);

    <T extends Persistable> T find(Object key);
}
