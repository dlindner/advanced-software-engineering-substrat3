/*
Adopted/inspired by the lecture Software Engineering 4th semester DHBW 2022 by Mr. Lutz
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.movie.manager.plugin.genericentitymanager;
import org.movie.manager.adapters.EntityManager;
import org.movie.manager.domain.FilmProfessional.FilmProfessional;
import org.movie.manager.domain.Metadata.Metadata;
import org.movie.manager.domain.Movie.Movie;
import org.movie.manager.domain.Persistable;

import java.util.*;
import java.util.stream.Collectors;

public class GenericEntityManager<T extends Persistable> implements EntityManager {
    private Map<Object, Persistable> allElements;

    public GenericEntityManager() {
        allElements = new HashMap();
    }

    public <T extends Persistable> boolean contains(T element) {
        return this.allElements.containsKey(element.getPrimaryKey());
    }

    public <T extends Persistable> void persist(T element) throws Exception {
        if (this.contains(element)) {
            throw new Exception("Element exist! ");
        } else {
            this.allElements.put(element.getPrimaryKey(), element);
        }
    }
    public <T extends Persistable> void remove(T element) {
        this.allElements.remove(element.getPrimaryKey());
    }

    public T find(Class<?> c, Object key) {
        Iterator var4 = this.allElements.values().iterator();

        Persistable t;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            t = (Persistable)var4.next();
        } while(!c.isInstance(t) || !t.getPrimaryKey().equals(key));

        return (T) t;
    }

    public <T extends Persistable> List<T> find(Class<?> c) {
        return (List)this.allElements.values().stream().filter((e) -> {
            return c.isInstance(e);
        }).collect(Collectors.toList());
    }

    public T find(Object key) {
        return (T) this.allElements.get(key);
    }
}

