package org.movie.manager.adapters.Events;

import java.util.EventListener;

public interface Observable {
    boolean addObserver(EventListener var1);

    boolean removeObserver(EventListener var1);
}
