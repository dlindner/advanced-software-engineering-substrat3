package org.movie.manager.adapters.Events;

import java.util.EventListener;

public interface UpdateEventListener extends EventListener {
    void processUpdateEvent(UpdateEvent event);
}
